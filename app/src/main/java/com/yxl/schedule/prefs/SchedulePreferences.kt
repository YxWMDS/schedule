package com.yxl.schedule.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SchedulePreferences @Inject constructor (@ApplicationContext val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "schedule")
    private val weekNumber = stringPreferencesKey("week_number")
    private val groupNumber = stringPreferencesKey("group_number")
    private val subgroupNumber = stringPreferencesKey("subgroup_number")

    suspend fun setGroup(group: String){
        context.dataStore.edit {
            it[groupNumber] = group
        }
    }

    suspend fun setSubgroup(subgroup: String){
        context.dataStore.edit {
            it[subgroupNumber] = subgroup
        }
    }

    suspend fun setWeekNumber(week: String){
        context.dataStore.edit {
            it[weekNumber] = week
        }
    }

    val getGroup : Flow<String?> = context.dataStore.data.map{
        it[groupNumber]
    }

    val getSubgroup : Flow<String?> = context.dataStore.data.map{
        it[subgroupNumber]
    }

    val getWeekNumber : Flow<String?> = context.dataStore.data.map{
        it[weekNumber]
    }

}