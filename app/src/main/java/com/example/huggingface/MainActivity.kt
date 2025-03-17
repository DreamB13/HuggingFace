package com.example.huggingface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.huggingface.Retrofit.EmotionRequest
import com.example.huggingface.Retrofit.RetrofitClient
import com.example.huggingface.Retrofit.RetrofitInstance2
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
    var inputText1 by remember { mutableStateOf("") }  // 입력된 문장
    var inputText2 by remember { mutableStateOf("") }  // 입력된 문장
    var resultText1 by remember { mutableStateOf("여기에 결과가 표시됩니다.") }  // 서버 응답 결과
    var resultText2 by remember { mutableStateOf("여기에 결과가 표시됩니다.") }  // 서버 응답 결과
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 입력 필드
        OutlinedTextField(
            value = inputText1,
            onValueChange = { inputText1 = it },
            label = { Text("문장을 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 버튼
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val response = RetrofitClient.apiService.generateText(TextRequest(inputText1))
                        resultText1 = response.generated_text  // 서버 응답을 표시
                    } catch (e: Exception) {
                        resultText1 = "오류 발생: ${e.message}"
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
            text = resultText1,
            modifier = Modifier.fillMaxWidth()
        )
        // 입력 필드
        OutlinedTextField(
            value = inputText2,
            onValueChange = { inputText2 = it },
            label = { Text("문장을 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 버튼
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val response = RetrofitInstance2.api.analyzeText(EmotionRequest(inputText2))
                        resultText2 = response.emotion  // 서버 응답을 표시
                    } catch (e: Exception) {
                        resultText2 = "오류 발생: ${e.message}"
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
            text = resultText2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

