package com.example.ramadanapp.features.sections.domain.interactors

import android.util.Log
import com.example.ramadanapp.common.domain.interactors.BaseUC
import com.example.ramadanapp.features.sections.domain.models.Section
import com.example.ramadanapp.features.sections.domain.repos.IMainSections

class GetMainSectionsUC(private val mainSectionsRepo: IMainSections) : BaseUC<Unit,List<Section>>(){
	override suspend fun execute(param: Unit?): List<Section> {
		val sections = mainSectionsRepo.getMainSections()
		sections.forEach{
			Log.d("TAG", "execute: ${it.title}")
		}
		return sections
	}

}