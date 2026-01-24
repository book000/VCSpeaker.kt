# AGENTS.md

## 目的
- エージェント共通の作業方針を定義する。

## 基本方針
- 会話言語: 日本語
- コード内コメント: 日本語
- エラーメッセージ: 英語
- PR とコミットは Conventional Commits に従う。
- PR タイトルとコミット本文の言語: PR タイトルは Conventional Commits 形式（英語推奨）。PR 本文は日本語。コミットは Conventional Commits 形式（description は日本語）。
- 日本語と英数字の間には半角スペースを挿入する。

## 判断記録のルール
- 判断内容、代替案、採用理由、前提条件、不確実性を明示する。

## 開発手順（概要）
1. プロジェクト理解のために必要なファイルを確認する。
2. 依存関係をインストールする。
3. 変更を実装する。
4. テストと Lint / Format を実行する。

## セキュリティ / 機密情報
- 認証情報やトークンはコミットしない。
- ログに機密情報を出力しない。

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
