package com.iscoding.common.models.domain

data class User(
    val id: String,
    val fullName: String,
    val phoneNumber: String,
    val email: String?,
    val nationalId: String,
    val isVerified: Boolean,
    val kycStatus: KycStatus
)
enum class KycStatus { NOT_STARTED,PENDING, VERIFIED, REJECTED }