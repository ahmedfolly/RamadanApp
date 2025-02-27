package com.da3wa.ramadanapp.common.domain.mapper

abstract class IMapper<Domain, Dto, Entity> {
	open fun fromDtoToDomain(dto: Dto): Domain =
		throw NotImplementedError("Try to implement fromDtoToEntity function")
	fun convertDtosToDomains(dtos: List<Dto>): List<Domain> {
		return dtos.map { dto-> fromDtoToDomain(dto) }
	}
//	open fun List<Dto>.convertDtoToDomains() = map { dto -> fromDtoToDomain(dto) }

	open fun toEntity(domain: Domain): Entity =
		throw NotImplementedError("Try to implement fromDtoToEntity function")

//	fun List<Domain>.convertToEntities() = map { domain -> toEntity(domain) }

	open fun fromEntityToDomain(entity: Entity?): Domain =
		throw NotImplementedError("Try to implement fromDtoToEntity function")

	fun convertEntitiesToDomains(entities: List<Entity>) : List<Domain>{
		return entities.map { entity-> fromEntityToDomain(entity) }
	}

	open fun fromDtoToEntity(dto: Dto): Entity =
		throw NotImplementedError("Try to implement fromDtoToEntity function")

//	fun List<Dto>.convertDtosToEntities() = map { dto -> fromDtoToEntity(dto) }

	fun fromEntityToDto(entity: Entity): Dto =
		throw NotImplementedError("Try to implement fromEntityToDto function")

	open fun fromDomainToEntity(domain: Domain):Entity =
		throw NotImplementedError("Try to implement fromDtoToEntity function")

}