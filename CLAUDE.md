# CLAUDE.md

## 目的
- Claude Code の作業方針とプロジェクト固有ルールを示す。

## 判断記録のルール
- 判断は必ずレビュー可能な形で記録する。
  1. 判断内容の要約
  2. 検討した代替案
  3. 採用しなかった案とその理由
  4. 前提条件・仮定・不確実性
  5. 他エージェントによるレビュー可否
- 前提・仮定・不確実性を明示し、仮定を事実のように扱わない。

## プロジェクト概要
Discord Botプロジェクト。テキストチャンネルのメッセージを音声で読み上げる機能を提供するDiscord Botです。VoiceTextAPI、LavaPlayer、Google Cloud Visionなどを利用した高度なテキスト音声変換・処理機能を備えています。

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

## 重要ルール
- 会話言語: 日本語
- PR とコミットは Conventional Commits に従う。
- PR タイトルとコミット本文の言語: PR タイトルは Conventional Commits 形式（英語推奨）。PR 本文は日本語。コミットは Conventional Commits 形式（description は日本語）。
- コメント言語: 日本語
- エラーメッセージ: 英語
- 日本語と英数字の間には半角スペースを挿入する。
- 既存のプロジェクトルールがある場合はそれを優先する。

## 環境のルール
- ブランチ命名は Conventional Branch に従う。
- GitHub リポジトリを調査する場合はテンポラリディレクトリに `git clone` して検索する。
- Windows 環境では Git Bash を使用する。
- Renovate の既存 PR には追加コミットしない。

## Git Worktree
- 使う場合は `.bare/<branch>` 構成で作成する。

## ブラウザ操作
- 座標ではなくセレクターで要素を特定する。
- 実装と画面の差異を確認し、必要に応じて実装を改善する。

## コード改修時のルール
- 既存のエラーメッセージで先頭に絵文字がある場合、全体で統一する。
- TypeScript 使用時は `skipLibCheck` で回避しない。
- 関数やインターフェースには docstring（JSDoc など）を記載する。

### コーディング規約
- **code_style**:
  - Official Kotlin code style
  - Uses Kord Extensions conventions
  - Package structure: com.jaoafa.vcspeaker.*
  - Separates concerns: commands, events, tts, stores, models, configs

## 相談ルール
- Codex CLI: 実装レビュー、局所設計、整合性確認に使う。
- Gemini CLI: 外部仕様や最新情報の確認に使う。
- 他エージェントの指摘は黙殺せず、採用または理由を明記して不採用とする。

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

### プロジェクト構造
**ルートファイル:**
- `build.gradle.kts`
- `gradle.properties`
- `settings.gradle.kts`
- `.releaserc (Semantic Release config)`
- `Dockerfile`
- `compose.yaml`
- `README.md`
- `LICENSE`
- `mkdocs.yml`
- `.gitattributes`

**主要ディレクトリ:**
- `src/main/kotlin/com/jaoafa/vcspeaker/ (Main source)`
- `src/test/kotlin/ (Tests)`
- `gradle/ (Gradle wrapper)`
- `docs/ (Documentation)`
- `scripts/ (Utility scripts)`
- `.github/workflows/ (CI/CD)`
- `.idea/ (IDE config)`

## 実装パターン
- 既存のコードパターンに従う。
- プロジェクト固有の実装ガイドラインがある場合はそれに従う。

## テスト
- 方針: 変更内容に応じてテストを追加する。

## ドキュメント更新ルール
- 更新タイミング: 実装確定後、同一コミットまたは追加コミットで更新する。
- README、API ドキュメント、コメント等は常に最新状態を保つ。

## 作業チェックリスト

### 新規改修時
1. プロジェクトを理解する。
2. 作業ブランチが適切であることを確認する。
3. 最新のリモートブランチに基づいた新規ブランチであることを確認する。
4. PR がクローズされた不要ブランチが削除済みであることを確認する。
5. 指定されたパッケージマネージャーで依存関係をインストールする。

### コミット・プッシュ前
1. Conventional Commits に従っていることを確認する。
2. センシティブな情報が含まれていないことを確認する。
3. Lint / Format エラーがないことを確認する。
4. 動作確認を行う。

### PR 作成前
1. PR 作成の依頼があることを確認する。
2. センシティブな情報が含まれていないことを確認する。
3. コンフリクトの恐れがないことを確認する。

### PR 作成後
1. コンフリクトがないことを確認する。
2. PR 本文が最新状態のみを網羅していることを確認する。
3. `gh pr checks <PR ID> --watch` で CI を確認する。
4. Copilot レビューに対応し、コメントに返信する。
5. Codex のコードレビューを実施し、指摘対応を行う。
6. PR 本文の崩れがないことを確認する。

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
