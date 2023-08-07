package com.example.task4_android_dounews_kotlin.model.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.task4_android_dounews_kotlin.model.local.room.ArticleSettingsDbEntity.Companion.TABLE_SETTING

@Entity(
    tableName = TABLE_SETTING,
    foreignKeys = [ForeignKey(
        entity = ArticleDbEntity::class,
        parentColumns = ["id"],
        childColumns = ["setting_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class ArticleSettingsDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "setting_id")
    val settingId: Long,
    @ColumnInfo(name = "is_selected", defaultValue = "false")
    val isSelected: Boolean
) {
    companion object {
        const val TABLE_SETTING = "table_setting"
    }
}