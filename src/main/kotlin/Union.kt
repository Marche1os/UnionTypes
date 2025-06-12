package ru.marche1os.turingmachine

sealed interface Union2<A, B> {
    fun <R> fold(ifA: (A) -> R, ifB: (B) -> R): R

    fun ofA(block: (A) -> Unit) {
        if (this is First) block(this.value)
    }

    fun ofB(block: (B) -> Unit) {
        if (this is Second) block(this.value)
    }

    companion object {
        fun <A, B> first(a: A): Union2<A, B> = First(a)
        fun <A, B> second(b: B): Union2<A, B> = Second(b)
    }

    data class First<A, B>(val value: A) : Union2<A, B> {
        override fun <R> fold(ifA: (A) -> R, ifB: (B) -> R): R = ifA(value)
    }

    data class Second<A, B>(val value: B) : Union2<A, B> {
        override fun <R> fold(ifA: (A) -> R, ifB: (B) -> R): R = ifB(value)
    }
}

fun <A, B> Union2<A, B>.getOrNullA(): A? = when (this) {
    is Union2.First -> value
    else -> null
}

fun <A, B> Union2<A, B>.getOrNullB(): B? = when (this) {
    is Union2.Second -> value
    else -> null
}

fun <A, B> union2(block: Union2Builder<A, B>.() -> Unit): Union2<A, B> = Union2Builder<A, B>().apply(block).build()

class Union2Builder<A, B> {
    private var a: A? = null
    private var b: B? = null

    fun a(value: A) {
        this.a = value
    }

    fun b(value: B) {
        this.b = value
    }

    fun build(): Union2<A, B> = when {
        a != null -> Union2.first(checkNotNull(a))
        b != null -> Union2.second(checkNotNull(b))
        else -> throw IllegalStateException("Union2: value must be set")
    }
}
