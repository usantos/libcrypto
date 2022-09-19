# Documentação Lib Crypto

Esta biblioteca foi criada com o intuito de proteger as requisições feitas pelo Aplicativo do Banco Carrefour. A biblioteca tem o formato aar, que é integrado via Android Gradle. A key e iv criptografados com RSA serão enviados via header na requisição, bem como o payload que será enviado so que em AES.

## Instalação

A instalação é feita pelo Android Gradle. 

Primeiro cole sua bilioteca com formato .aar no diretório libs e depois use a tag implementation file para passar o caminho

Na seção implementation do Gradle coloque o caminho da biblioteca.

```kotlin
  implementation files('libs/libcrypto-release.aar')
```

## Como Usar

Na classe a ser usada faça a importação da biblioteca.

```kotlin
  import br.com.libcrypto.Crypto
```

## Documentação da Biblioteca

#### Encripta os dados usando AES

```kotlin
  Crypto.encryptAES('data')
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `data` | `string` | **Obrigatório**. O dado a ser criptografado |

#### Retorna o dado criptografado em AES

#### Decripta os dados que foi previamente encriptado com AES

```kotlin
  Crypto.decryptAES('data')
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `data` | `string` | **Obrigatório**. O dado a ser decriptado |

#### Retorna o dado decriptado

#### Decripta os dados que foi previamente encriptado com AES

```kotlin
  Crypto.encryptRSA('data','publicCertificate')
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `data` | `string` | **Obrigatório**. O dado a ser criptografado |
| `publicCertificate` | `string` | **Obrigatório**. O certificado público |

#### Retorna o dado criptografado em RSA

## Contribuição
Esta biblioteca é mantida pela equipe de SI do Banco Carrefour. 

## Licença
Todos os direitos reservados ao Banco Carrefour.
