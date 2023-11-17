# One Pick
![OnePick-Logo](https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp)

## 概要
**One Pick** は「今のあなたの気分にぴったり」な映画をオススメするAndroidアプリです。<br>
最大3つのキーワードを入力するだけで関連する映画を1つピックアップし、レコメンドします。映画の詳細については画面より確認することができます。

## アプリの使い方
| Screen | Description |
|---|---|
|<img src="https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/image/Screenshot_howto_1.png" width="240"/>|1. キーワード（最大3つまで）を入力し、「Search」ボタンをタップします。<br>*1つのキーワードあたり、10文字まで入力可能です<br>*全キーワード未入力は許可されていません|
|<img src="https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/image/Screenshot_howto_2.png" width="240"/>|2. 検索中です。少し待ちます。|
|<img src="https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/image/Screenshot_howto_3.png" width="240"/>|3. オススメの映画が1つピックアップされました。映画の詳細を確認してください。検索画面に戻りたい場合は、画面右上の「X」ボタンをタップします。|
|<img src="https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/image/Screenshot_howto_4.png" width="240"/><img src="https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/image/Screenshot_howto_5.png" width="240"/>|Error: サーバーとの接続に失敗もしくは入力したキーワードで映画が見つからなかった場合はエラー画面が表示されます。「Retry」ボタンをタップして検索画面に戻ってください。|

### 動画で動作確認
![howto-use](https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/image/howto.gif)

## 技術スタック
### 開発言語 / フレームワーク / ライブラリ etc
Kotlin / Android Studio / Gradle Kotlin DSL / Jetpack Compose / Kotlin Coroutines / Retrofit / Coil Compose / Kotlin Serialization / Material Design3
### アーキテクチャ
MVVM
### コード管理
GitHub

## ローカル環境での実行
### 事前準備
1. Android Studioをインストールする<br>
https://developer.android.com/studio/install?hl=ja

2. OpenAIとTMDBのAPIキーを取得する<br>
`OpenAI:` https://openai.com/<br>
`TMDB:` https://themoviedb.org

### 起動
1. リポジトリのクローンを作成する
```shell
$ git clone https://github.com/haru-ish/one-pick.git
  
$ cd one-pick
```
2. `local.properties`ファイルを作成し、OpenAIとTMDBそれぞれのAPIキーをファイル内に指定する。このファイルを`one-pick/app`と同じ階層に追加する

```:local.properties
// Android SDKの場所を指定（下記は例）
sdk.dir=/Users/UserName/Library/Android/sdk

// 自身のAPIキーを指定
chatgpt_api_key=your-own-openai-apiKey
tmdb_api_key=your-own-tmdb-apiKey
```

3. Android Studioでプロジェクトを立ち上げ、エミュレーターか実機を選択し、`Run'app'`ボタンを押下してアプリを実行する