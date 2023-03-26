# Validator

![Foreman Logo](/assets/play-store.png)

[![Maven Central](https://img.shields.io/maven-central/v/by.shostko/validator?style=flat)](#integration) [![API-level](https://img.shields.io/badge/API-21+-blue?style=flat&logo=android)](https://source.android.com/setup/start/build-numbers) [![License](https://img.shields.io/badge/license-Apach%202.0-green?style=flat)](#license)

## How to use

### Create `Validation` entity

**Simple way**
```kotlin  
val email by EmailValidator()  
```  

**Composition of validators**
```kotlin  
val username by validators(  
  NotEmptyValidator(),
  LengthOverOrEqualValidator(3), 
)
```  

**Your own validator**
```kotlin  
val username = Validation(
  object : Validator<String, String> {  
    override suspend fun invoke(value: String): ValidationResult<String, String> =
      if (repository.checkIfUsernameAvailable(value)) {
        // you can even make network request here
        ValidationResult.valid(value)  
      } else {  
        ValidationResult.invalid(value, "Username already taken")  
      }  
  }
)
```  

### Bind `Validation` to JetpackCompose `TextField`
```kotlin    
@Composable  
private fun SampleTextField(  
  label: String,  
  validation: Validation<String, String>,  
  modifier: Modifier = Modifier,  
) {  
  val value by validation.valueFlow.collectAsState("")  
  val result by validation.resultFlow.collectAsState(ValidationResult.valid(""))  
  OutlinedTextField(  
    value = value,
    onValueChange = validation::onNewValue,
    label = { Text(label) },
    supportingText = { SampleErrorText(result) },
    isError = !result.isValid,
    modifier = modifier,
    singleLine = true,
  )  
}  
  
@Composable  
private fun SampleErrorText(result: ValidationResult<*, *>) {  
  if (result is ValidationResult.Invalid) {  
    val text = when (val reason = result.reason) {  
      is String -> reason  
      is Int -> stringResource(reason)  
      is ValidationException -> reason.reason  
      is Throwable -> reason.message  
      else -> null
      // any other optinal way to get reason from your custom Validator 
    }
    Text(  
      modifier = Modifier.fillMaxWidth(),  
      text = text ?: "Unknown error!",  
      textAlign = TextAlign.Start,  
    )  
  }
}
```

## Integration

The library is available in Maven Central repository:
```gradle
dependencies {
    implementation 'by.shostko:validator:0.+'
}
```

You can also use collections of ready-to-use validators:
```gradle
dependencies {
    implementation 'by.shostko:validator-exceptions:0.+' // result is Exception
    implementation 'by.shostko:validator-strings:0.+'    // result is pure String
    implementation 'by.shostko:validator-android:0.+'    // result is android resource id (Int) 
}
```

### License

Released under the [Apache 2.0 license](LICENSE).

```
Copyright 2019 Sergey Shostko

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
