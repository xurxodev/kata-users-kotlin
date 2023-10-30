package domain.functional

import domain.functional.Either
import domain.functional.flatMap
import domain.functional.map
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EitherShould {

    @Test
    fun `return right type if result is success`() {

        val obj = givenAMyObject()
        val successResult = Either.Right(obj)

        successResult.fold({ fail() },
            { right ->
                assertTrue(right::class.java == MyObject::class.java)
                assertTrue(right == obj)
            })
    }

    @Test
    fun `return left type if result is fail`() {

        val failure = MyObjectFailure.NetworkConnection
        val failResult: Either<MyObjectFailure, MyObject> = Either.Left(failure)

        failResult.fold({ left ->
            assertTrue(left::class.java == MyObjectFailure.NetworkConnection::class.java)
        }, { fail() })
    }

    @Test
    fun `return mapped right type after map success result`() {

        val obj = givenAMyObject()
        val successResult: Either<MyObjectFailure, MyObject> = Either.Right(obj)

        val mappedResult = successResult.map { mapMyObject(it) }

        mappedResult.fold({ fail() },
            { right ->
                assertTrue(right.title.contains(" mapped"))
            })
    }

    @Test
    fun `return left type after map fail result`() {

        val failure = MyObjectFailure.ObjectNotFound
        val failResult: Either<MyObjectFailure, MyObject> = Either.Left(failure)

        val resultMapped = failResult.map { mapMyObject(it) }

        resultMapped.fold({ left -> assertTrue(left == failure) }, { fail() })
    }

    @Test
    fun `return mapped list right type after map a success list result`() {

        val objList = listOf(givenAMyObject("object 1"), givenAMyObject("object 2"))
        val objListSuccessResult: Either<MyObjectFailure, List<MyObject>> = Either.Right(objList)

        val resultMapped = objListSuccessResult.map { it.map { mapMyObject(it) } }

        resultMapped.fold({ fail() },
            { right ->
                for (obj in right)
                    assertTrue(obj.title.contains(" mapped"))
            })
    }

    @Test
    fun `return expected value after flatmap children and map a success result`() {

        val obj = givenAMyObject()
        val successResult = Either.Right(obj)

        val numChildrenResultCase1 = successResult
            .flatMap { returnedObj ->
                getChildrenOfParent(returnedObj.id)
                    .map { actors -> actors.size }
            }

        val numChildrenResultCase2 = successResult
            .flatMap { returnedObj -> getChildrenOfParent(returnedObj.id) }
            .map { actors -> actors.size }

        numChildrenResultCase1.fold({ fail() },
            { right -> assertEquals(5, right) })

        numChildrenResultCase2.fold({ fail() },
            { right -> assertEquals(5, right) })
    }

    private fun getChildrenOfParent(parentId: Long): Either<MyObjectFailure, List<String>> {
        val interpretersList =
            listOf("Children 1", "Children 2", "Children 3", "Children 4", "Children 5")
        return Either.Right(interpretersList)
    }

    private fun givenAMyObject(title: String = "object 1"): MyObject =
        MyObject(1, title, "url", "overview")

    private fun mapMyObject(it: MyObject) =
        MyObject(it.id, it.title + " mapped", it.url, it.overview)
}

data class MyObject(val id: Long, val title: String, val url: String, val overview: String)

sealed class MyObjectFailure {
    data object NetworkConnection : MyObjectFailure()
    data object ObjectNotFound : MyObjectFailure()
}