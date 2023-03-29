package io.github.mrvictor42.mapper

import com.github.dozermapper.core.DozerBeanMapperBuilder
import com.github.dozermapper.core.Mapper

object DozerMapper {

    private val mapper : Mapper = DozerBeanMapperBuilder.buildDefault()

    fun <O, D> parserObject(origin : O, destination: Class<D>?) : D {
        return mapper.map(origin, destination)
    }

    fun <O, D> parserListObjects(origin : List<O>, destination: Class<D>?) : List<D> {
        val destinationObjects : ArrayList<D> = ArrayList()

        origin.stream().forEach { o -> destinationObjects.add(mapper.map(o, destination)) }

        return destinationObjects
    }
}