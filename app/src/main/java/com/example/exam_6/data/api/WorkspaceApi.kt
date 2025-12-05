package com.example.exam_6.data.api

import com.example.exam_6.data.WorkspaceDto
import retrofit2.http.GET

interface WorkspaceApi {
    @GET("v1/e3215354-6784-4bae-9bb9-25b39360971b")
    suspend fun getWorkspaces(): List<WorkspaceDto>
}