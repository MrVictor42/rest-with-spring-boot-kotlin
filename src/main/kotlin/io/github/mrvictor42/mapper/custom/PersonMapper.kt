package io.github.mrvictor42.mapper.custom

import io.github.mrvictor42.data.vo.v1.PersonVO
import io.github.mrvictor42.model.Person
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonMapper {

    fun mapEntityToVo(person : Person) : PersonVO {
        val vo = PersonVO()

        vo.id = person.id
        vo.firstName = person.firstName
        vo.lastName = person.lastName
        vo.address = person.address
        vo.gender = person.gender
//        vo.birthDay = Date()

        return vo
    }

    fun mapVOToEntity(person : PersonVO) : Person {

        val entity = Person()

        entity.id = person.id
        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
//        entity.birthDay = person.birthDay

        return entity
    }
}