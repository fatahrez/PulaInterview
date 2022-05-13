package com.fatahapps.presentation.viewmodel.questions

sealed class QuestionEvent {
    object ProgressIndicator: QuestionEvent()
    object NextQuestion: QuestionEvent()
    object GetQuestions: QuestionEvent()
    object NavigateToAfterQuestion: QuestionEvent()
}