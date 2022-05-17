package com.fatahapps.presentation.viewmodel.questions

import com.fatahapps.presentation.model.survey.EngStrings

sealed class QuestionEvent {
    object ProgressIndicator: QuestionEvent()
    object NextQuestion: QuestionEvent()
    object GetQuestions: QuestionEvent()
    object GetEngStrings: QuestionEvent()
    object GetPhoto: QuestionEvent()
    object NavigateToAfterQuestion: QuestionEvent()
}