package ru.marche1os.turingmachine

fun main() {
    val cond = false

    val premiumOrJustUser = union2 {
        if (cond) {
            a(PremiumUser(
                name = "PremiumUser",
                "business"
            ))
        }

        b(
            JustUser(
                name = "justUser",
                bio = "bio"
            )
        )
    }

    process(premiumOrJustUser)
}

fun process(user: Union2<PremiumUser, JustUser>) {
    user.ofA { premiumUser ->
        println(premiumUser)
    }

     user.ofB { user ->
        println(user)
    }
}

data class PremiumUser(
    val name: String,
    val businessLink: String
)

data class JustUser(
    val name: String,
    val bio: String,
)