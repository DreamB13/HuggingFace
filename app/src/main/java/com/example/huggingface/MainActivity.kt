package com.example.huggingface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.huggingface.Retrofit.RetrofitClient
import com.example.huggingface.Retrofit.TextRequest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var inputText by remember { mutableStateOf("") }  // 입력된 문장
    var resultText by remember { mutableStateOf("여기에 결과가 표시됩니다.") }  // 서버 응답 결과
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 입력 필드
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("문장을 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 버튼
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val response = RetrofitClient.apiService.generateText(TextRequest(inputText))
                        resultText = response.generated_text  // 서버 응답을 표시
                    } catch (e: Exception) {
                        resultText = "오류 발생: ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("전송")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 결과 출력
        Text(
            text = resultText,
            modifier = Modifier.fillMaxWidth()
        )
    }
}