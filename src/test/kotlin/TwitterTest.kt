import com.jaoafa.vcspeaker.tools.Twitter
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class TwitterTest : FunSpec({
    test("If the tweet exists, the tweet information should be returned.") {
        val tweet = Twitter.getTweet("jaoafa", "1685568414084124673")

        tweet.shouldNotBeNull()
        tweet.authorName.shouldNotBeNull()
        tweet.html.shouldNotBeNull()
        tweet.plainText.shouldNotBeNull()
        tweet.readText.shouldNotBeNull()

        tweet.authorName shouldBe "jao Community"
        tweet.html shouldBe "<blockquote class=\"twitter-tweet\"><p lang=\"ja\" dir=\"ltr\">この度、jao Minecraft Serverでは2023年08月02日 22時00分をもって、Minecraft サーバのサービス提供を終了させていただくこととなりました。<br>利用者のみなさまには、突然のお知らせとなりますことをお詫びいたします。<br><br>7年間、本当にありがとうございました。<a href=\"https://twitter.com/hashtag/jaoafa?src=hash&amp;ref_src=twsrc%5Etfw\">#jaoafa</a></p>&mdash; jao Community (@jaoafa) <a href=\"https://twitter.com/jaoafa/status/1685568414084124673?ref_src=twsrc%5Etfw\">July 30, 2023</a></blockquote>\n<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>\n\n"
        tweet.plainText shouldBe "この度、jao Minecraft Serverでは2023年08月02日 22時00分をもって、Minecraft サーバのサービス提供を終了させていただくこととなりました。\n利用者のみなさまには、突然のお知らせとなりますことをお詫びいたします。\n\n7年間、本当にありがとうございました。#jaoafa <https://twitter.com/hashtag/jaoafa?src=hash&ref_src=twsrc%5Etfw>"
        tweet.readText shouldBe "この度、jao Minecraft Serverでは2023年08月02日 22時00分をもって、Minecraft サーバのサービス提供を終了させていただくこととなりました。\n利用者のみなさまには、突然のお知らせとなりますことをお詫びいたします。\n\n7年間、本当にありがとうございました。 ハッシュタグ「jaoafa」"
    }

    test("If the tweet does not exist, null should be returned.") {
        val tweet = Twitter.getTweet("jaoafa", "0")

        tweet.shouldBeNull()
    }

    test("If the tweet with picture exists, the tweet information should be returned.") {
        val tweet = Twitter.getTweet("jaoafa", "1775559092742021223")

        tweet.shouldNotBeNull()
        tweet.authorName.shouldNotBeNull()
        tweet.html.shouldNotBeNull()
        tweet.plainText.shouldNotBeNull()
        tweet.readText.shouldNotBeNull()

        tweet.authorName shouldBe "jao Community"
        tweet.html shouldBe "<blockquote class=\"twitter-tweet\"><p lang=\"en\" dir=\"ltr\">Do you remember when you joined X? I do! <a href=\"https://twitter.com/hashtag/MyXAnniversary?src=hash&amp;ref_src=twsrc%5Etfw\">#MyXAnniversary</a> <a href=\"https://t.co/JbXvgioO6o\">pic.twitter.com/JbXvgioO6o</a></p>&mdash; jao Community (@jaoafa) <a href=\"https://twitter.com/jaoafa/status/1775559092742021223?ref_src=twsrc%5Etfw\">April 3, 2024</a></blockquote>\n<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>\n\n"
        tweet.plainText shouldBe "Do you remember when you joined X? I do! #MyXAnniversary <https://twitter.com/hashtag/MyXAnniversary?src=hash&ref_src=twsrc%5Etfw> pic.twitter.com/JbXvgioO6o <https://t.co/JbXvgioO6o>"
        tweet.readText shouldBe "Do you remember when you joined X? I do!  ハッシュタグ「MyXAnniversary」"
    }
})