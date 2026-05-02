package com.techpulse.user

import com.techpulse.common.exception.ApiException
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class TechStackRequest(
    val technologies: List<String>,
    val interestLevel: InterestLevel
)

data class UserStats(
    val userId: Long,
    val email: String,
    val name: String,
    val techStack: TechStackRequest?,
    val newsletterCount: Long,
    val activeLearningPlans: Long
)

sealed class UserResult<out T> {
    data class Success<T>(val data: T) : UserResult<T>()
    data class NotFound(val message: String) : UserResult<Nothing>()
}

@Service
class UserService(
    private val userRepository: UserRepository,
    private val techStackRepository: TechStackRepository
) {

    @Transactional
    @CacheEvict(value = ["userStats"], key = "#userId")
    fun updateTechStack(userId: Long, request: TechStackRequest): UserResult<TechStack> {
        if (!userRepository.existsById(userId)) {
            return UserResult.NotFound("User not found")
        }

        val existing = techStackRepository.findByUserId(userId)
        val techStack = if (existing != null) {
            techStackRepository.save(
                TechStack(
                    id = existing.id,
                    userId = userId,
                    technologies = request.technologies,
                    interestLevel = request.interestLevel
                )
            )
        } else {
            techStackRepository.save(
                TechStack(
                    userId = userId,
                    technologies = request.technologies,
                    interestLevel = request.interestLevel
                )
            )
        }
        return UserResult.Success(techStack)
    }

    @Cacheable(value = ["userStats"], key = "#userId")
    fun getUserStats(userId: Long): UserResult<UserStats> {
        val user = userRepository.findById(userId).orElse(null)
            ?: return UserResult.NotFound("User not found")

        val techStack = techStackRepository.findByUserId(userId)

        return UserResult.Success(
            UserStats(
                userId = user.id,
                email = user.email,
                name = user.name,
                techStack = techStack?.let {
                    TechStackRequest(it.technologies, it.interestLevel)
                },
                newsletterCount = 0, // TODO: wire to newsletter repository
                activeLearningPlans = 0 // TODO: wire to learning plan repository
            )
        )
    }
}
