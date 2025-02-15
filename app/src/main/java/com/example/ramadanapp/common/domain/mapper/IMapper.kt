package com.example.ramadanapp.common.domain.mapper

abstract class IMapper<Domain, Dto, Entity> {
	open fun Dto.fromDtoToDomain(): Domain =
		throw NotImplementedError("Try to implement fromDtoToEntity function")

	open fun List<Dto>.convertDtoToDomains() = map { dto -> dto.fromDtoToDomain() }

	open fun Domain.toEntity(): Entity =
		throw NotImplementedError("Try to implement fromDtoToEntity function")

	fun List<Domain>.convertToEntities() = map { domain -> domain.toEntity() }

	open fun Entity.fromEntityToDomain(): Domain =
		throw NotImplementedError("Try to implement fromDtoToEntity function")

	fun List<Entity>.convertEntitiesToDomains() = map { entity -> entity.fromEntityToDomain() }

	open fun Dto.fromDtoToEntity(): Entity =
		throw NotImplementedError("Try to implement fromDtoToEntity function")

	fun List<Dto>.convertDtosToEntities() = map { dto -> dto.fromDtoToEntity() }

	fun Entity.fromEntityToDto(): Dto =
		throw NotImplementedError("Try to implement fromEntityToDto function")
}