package com.example.task4_android_dounews_kotlin.model.mappers

interface NewsMapper<DbEntity, UiEntity, ResponseEntity> {

    fun fromDbEntity(dbEntity: DbEntity): UiEntity
    fun fromUiEntity(uiEntity: UiEntity): DbEntity
    fun fromResponseEntity(responseEntity: ResponseEntity): DbEntity
    fun fromResponseEntityList(responseEntityList: List<ResponseEntity>): List<DbEntity>
    fun toUiEntityList(dbEntityList: List<DbEntity>): List<UiEntity>
}