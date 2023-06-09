package com.bangkit.coffee.presentation.verifyotp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.verifyotp.components.OTPTextField
import com.bangkit.coffee.presentation.verifyotp.components.VerifyOTPForm
import com.bangkit.coffee.shared.const.STATIC_URL
import com.bangkit.coffee.shared.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.naingaungluu.formconductor.FormResult
import me.naingaungluu.formconductor.composeui.field
import me.naingaungluu.formconductor.composeui.form

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerifyOTPScreen(
    state: VerifyOTPState = VerifyOTPState(),
    actions: VerifyOTPActions = VerifyOTPActions()
) {
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .aspectRatio(1f),
            model = ImageRequest.Builder(context)
                .decoderFactory(SvgDecoder.Factory())
                .data(STATIC_URL + "enter_otp.svg")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.enter_otp),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.enter_otp_description),
            style = MaterialTheme.typography.bodyMedium,
        )
        state.email?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        form(VerifyOTPForm::class) {
            field(VerifyOTPForm::code) {
                OTPTextField(
                    value = this.state.value?.value.orEmpty(),
                    onValueChange = this::setField,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch {
                                    delay(100)
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                        }
                        .testTag("OTPField"),
                )

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val formData = formState.value
            Button(
                onClick = {
                    if (formData is FormResult.Success){
                        actions.verifyOTP(formData.data.code)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .testTag("VerifyOTPButton"),
                enabled = this.formState.value is FormResult.Success
            ) {
                Text(text = stringResource(R.string.verify))
            }
        }
    }
}

@Composable
@Preview(name = "VerifyOTP", showBackground = true)
private fun VerifyOTPScreenPreview() {
    AppTheme {
        VerifyOTPScreen()
    }
}

