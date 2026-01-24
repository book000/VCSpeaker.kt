# GitHub Copilot Instructions

## プロジェクト概要
Discord Botプロジェクト。テキストチャンネルのメッセージを音声で読み上げる機能を提供するDiscord Botです。VoiceTextAPI、LavaPlayer、Google Cloud Visionなどを利用した高度なテキスト音声変換・処理機能を備えています。

## 共通ルール
- 会話は日本語で行う。
- PR とコミットは Conventional Commits に従う。
- PR タイトルとコミット本文の言語: PR タイトルは Conventional Commits 形式（英語推奨）。PR 本文は日本語。コミットは Conventional Commits 形式（description は日本語）。
- 日本語と英数字の間には半角スペースを入れる。
- 既存のプロジェクトルールがある場合はそれを優先する。

### 技術スタック
- **言語**: Kotlin, Python
- **フレームワーク**: Kord Extensions (Discord Bot Framework), LavaPlayer (Audio Processing), Ktor (HTTP Client), Gradle (Build System)
- **パッケージマネージャー**: gradle@7.x (with gradlew wrapper)
- **主要な依存関係**:
  - dev.kord:kord-core (Discord API)
  - dev.kord:kord-voice (Voice Support)
  - com.kotlindiscord.kord.extensions:kord-extensions:1.6.0
  - dev.arbjerg:lavaplayer:2.2.1
  - org.slf4j:slf4j-api:2.1.0-alpha1
  - org.apache.logging.log4j:log4j-core:2.23.1
  - com.google.cloud:google-cloud-vision:3.32.0
  - com.sksamuel.scrimage:scrimage-core:4.1.3
  - io.sentry:sentry:7.12.0
  - net.htmlparser.jericho:jericho-html:3.4

### コーディング規約
- **code_style**:
  - Official Kotlin code style
  - Uses Kord Extensions conventions
  - Package structure: com.jaoafa.vcspeaker.*
  - Separates concerns: commands, events, tts, stores, models, configs

### 開発コマンド
```bash
# install
./gradlew build

# build
./gradlew build

# test
./gradlew test

# lint
No explicit lint command found

# format
Code style: kotlin.code.style=official

# dev
Run with: java -jar vcspeaker-kt.jar

# other commands
./gradlew run (Run application)
docker build -t vcspeaker-kt . (Docker build)
docker-compose up (Docker compose run)
```

## テスト方針
- 新機能や修正には適切なテストを追加する。

## セキュリティ / 機密情報
- 認証情報やトークンはコミットしない。
- ログに機密情報を出力しない。

## ドキュメント更新
- 実装確定後、同一コミットまたは追加コミットで更新する。
- README、API ドキュメント、コメント等は常に最新状態を保つ。

## リポジトリ固有
- **type: Discord Bot (Voice Channel Text Reader)**
- **uses: Kord library for Discord integration**
- **uses: VoiceText API for text-to-speech conversion**
- **uses: Google Cloud Vision API for image text extraction**
- **uses: LavaPlayer for audio playback**
- **features: Message replacement/filtering (replacers for URLs, emojis, mentions, etc.)**
- **features: Guild-specific configuration and storage**
- **features: Supports Twitter and YouTube metadata extraction**
- **deployment: Docker container with Alpine Linux + OpenJDK 17**
- **font_support: Includes IPA fonts for Japanese character support**
- **timezone: Asia/Tokyo configured in Docker**
- **release_strategy: Semantic Release with dryRun enabled**
- **testing: Unit tests with Kotest and MockK**
- **java_version: JVM toolchain 17 required**
- **main_class: com.jaoafa.vcspeaker.MainKt**
- **jar_output: vcspeaker-kt.jar (fat JAR with dependencies)**
- **ci_workflows: codeql.yml, docker.yml, mkdocs.yml, release.yml, review.yml, test.yml**
