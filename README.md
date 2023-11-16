# One Pick
![OnePick-Logo](https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp)
<br>
## 概要
**One Pick** は「今のあなたの気分にぴったり」な映画をオススメするAndroidアプリです。<br>
最大3つのキーワードを入力するだけで関連する映画を1つピックアップし、レコメンドします。映画の詳細については画面より確認することができます。
<br>
## アプリの使い方
| ---- | ---- |
| <img src="https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/image/Screenshot_howto_1.png" width="240" /> | 1. キーワード（最大3つまで）を入力し、「Search」ボタンをタップします。<br>*1つのキーワードあたり、10文字まで入力可能です。<br>*全キーワード未入力は許可されていません。 |
<br>

<img src="https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/image/Screenshot_howto_2.png" width="240" /><br>
2. 検索中です。少し待ちます。<br>
<img src="https://github.com/haru-ish/one-pick/blob/main/app/src/main/res/image/Screenshot_howto_3.png" width="240" /><br>
3. オススメの映画が1つピックアップされました。映画の詳細を確認してください。検索画面に戻りたい場合は、画面右上の「X」ボタンをタップします。
Recording your mood on a daily basis can help you to understand your current mental state.

This app will help you to create habits that will keep you in good mental shape.

Record whether you have done the good habits along with how you feel today!

https://user-images.githubusercontent.com/108800859/198257732-0e9983b7-6a5e-4313-bc8f-0a6deeb860b7.mp4



https://user-images.githubusercontent.com/108800859/198259491-639cf6cd-8112-4cb0-9b42-5a476234a1dc.mp4



We hope **Rabbit Habit** will help you take a moment to reflect on how you felt today and what you did to make yourself happy. :green_heart:

## Demo
https://rabbit-habit.haruish.de

## Run your own server

### Preparation and Installation
1. Clone Repository
```shell
$ git clone https://github.com/haru-ish/RabbitHabit.git
  
$ cd RabbitHabit
```
2. Create firebase project

3. Download service account file from firebase terminal at `Project settings -> Service accounts -> Generate new private key`. Add this file to the classpath as `src/main/resources/service-account-file.json`

4. Add config from firebase terminal at `Project-settings -> General -> Your apps -> SDK setup and configuration -> Config` to `frontend/src/firebaseConfig.js`

5. (optional, necessary to run tests) Update both `application.yml` in `src/main/resources` and `src/test/resources` (they can be replaced with the command-line parameters in *Running the server: Locally*)

6. Compile the project: `$ mvn install -Dmaven.test.skip=true` (if you have updated `src/test/resources/application.yml` and started the PostgreSQL as described below, you can omit `-Dmaven.test.skip=true` in order to run the tests)

#### When running locally...

7. Install PostgreSQL and setup user as `rabbitdb` and database as `rabbitdb`

8. Import the schema with `schema.sql`
<!-- `psql -U rabbitdb rabbitdb < schema.sql` -->

#### When running Docker...

7. Build the Docker image: `$ docker build -t rabbit-habit .`

8. Create a volume to store a persistent database: `$ docker volume create rabbitdb`


### Running the server

#### Locally
Execute the compiled jar file to start the server with custom parameters (the frontend is included in the jar and served automatically):
```shell
$ java -jar target/RabbitTracker-0.0.1-SNAPSHOT.jar \
   --server.port=4278 \
   --spring.web.resources.static-locations=classpath:/frontend/ \
   --spring.datasource.driver-class-name=org.postgresql.Driver \
   --spring.datasource.url="jdbc:postgresql://localhost:5432/rabbitdb?serverTimezone=UTC" \
   --spring.datasource.username="rabbitdb" \
   --spring.datasource.password="your-database-password" \
   --spring.sql.init.encoding="UTF-8" \
   --spring.security.oauth2.resourceserver.jwt.jwk-set-uri="https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com" \
   --spring.security.oauth2.resourceserver.jwt.issuer-uri="https://securetoken.google.com/your-firebase-projectId"
```

#### Docker
Run the (previously built) Docker container using:

```shell
$ docker run -it --rm --name=rabbit-habit -e FIREBASE_ID=your-firebase-projectId -v rabbitdb:/var/lib/postgresql/data -p 4278:4278 rabbit-habit
```


Access the front-end Web page at: http://localhost:4278