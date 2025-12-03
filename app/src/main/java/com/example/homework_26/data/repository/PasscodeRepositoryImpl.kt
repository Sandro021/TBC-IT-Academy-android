package com.example.homework_26.data.repository

import com.example.homework_26.domain.repository.PasscodeRepository
import javax.inject.Inject

class PasscodeRepositoryImpl @Inject constructor(

) : PasscodeRepository {
    override fun getStoredPasscode(): String = "0934"

}