package io.github.mrvictor42.mapper

import org.modelmapper.ModelMapper

object DozerMapper {

//    private val mapper : Mapper = DozerBeanMapperBuilder.buildDefault()
    private val mapper : ModelMapper = ModelMapper()

    fun <O, D> parserObject(origin : O, destination: Class<D>?) : D {
        return mapper.map(origin, destination)
    }

    fun <O, D> parserListObjects(origin : List<O>, destination: Class<D>?) : ArrayList<D> {
        val destinationObjects : ArrayList<D> = ArrayList()

        origin.stream().forEach { o ->
            destinationObjects.add(mapper.map(o, destination))
        }

        return destinationObjects
    }
}