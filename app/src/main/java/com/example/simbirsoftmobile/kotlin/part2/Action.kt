package com.example.simbirsoftmobile.kotlin.part2

sealed class Action {
    object Registration : Action()
    class Login(val user: User) : Action()
    object Logout : Action()
}
