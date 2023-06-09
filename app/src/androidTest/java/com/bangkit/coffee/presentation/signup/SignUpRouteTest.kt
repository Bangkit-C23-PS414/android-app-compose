package com.bangkit.coffee.presentation.signup

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.bangkit.coffee.MainActivity
import com.bangkit.coffee.util.AppTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SignUpRouteTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun should_disableButton_when_formClean() {
        rule.activity.setContent { AppTest { SignUpRoute() } }

        rule.onNodeWithTag("SignUpButton").assertIsNotEnabled()
    }

    @Test
    fun should_enableButton_when_formValid() {
        rule.activity.setContent { AppTest { SignUpRoute() } }

        // Fill full name
        rule.onNodeWithTag("FullNameField").assertIsNotFocused()
        rule.onNodeWithTag("FullNameField").performTextInput("John")
        rule.onNodeWithTag("FullNameField").performImeAction()
        rule.onNodeWithTag("SignUpButton").assertIsNotEnabled()

        // Fill email
        rule.onNodeWithTag("EmailField").assertIsFocused()
        rule.onNodeWithTag("EmailField").performTextInput("test@gmail.com")
        rule.onNodeWithTag("EmailField").performImeAction()
        rule.onNodeWithTag("SignUpButton").assertIsNotEnabled()

        // Fill password
        rule.onNodeWithTag("PasswordField").assertIsFocused()
        rule.onNodeWithTag("PasswordField").performTextInput("password")
        rule.onNodeWithTag("PasswordField").performImeAction()
        rule.onNodeWithTag("SignUpButton").assertIsNotEnabled()

        // Fill confirm password
        rule.onNodeWithTag("ConfirmPasswordField").assertIsFocused()
        rule.onNodeWithTag("ConfirmPasswordField").performTextInput("password")
        rule.onNodeWithTag("ConfirmPasswordField").performImeAction()
        rule.onNodeWithTag("SignUpButton").assertIsNotEnabled()

        // Form Conductor Bug
        rule.onNodeWithTag("FullNameField").performTextReplacement("John Doe")

        rule.onNodeWithTag("SignUpButton").assertIsEnabled()
    }
}