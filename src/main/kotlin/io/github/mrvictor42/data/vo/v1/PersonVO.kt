package io.github.mrvictor42.data.vo.v1

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.hateoas.RepresentationModel

@JsonPropertyOrder("id", "address", "first_name", "last_name", "gender")
data class PersonVO (
    var id : Long = 0,
    @field:JsonProperty("first_name") //para serialização no json final
    var firstName : String = "",
    @field:JsonProperty("last_name") //para serialização no json final
    var lastName : String = "",
    var address : String = "",
    @field:JsonIgnore
    var gender : String = "",
) : RepresentationModel<PersonVO>()