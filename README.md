# VCSpeaker.kt

🔊 Text channel, now with voice - VCSpeaker

## Getting Started

1. Download the latest release from [Releases](https://github.com/jaoafa/VCSpeaker.kt/releases).
2. Put the jar file in the new folder (e.g. `vcspeaker`)
3. Create a `config.yml` file in the same folder as the jar file.
4. Write the following in `config.yml`:
    ```yaml
    token:
      discord: <Discord Bot Token>
      discord_dev: <Discord Bot Token for development>
      voicetext: <VoiceText API Token>
      voicetext_dev: <VoiceText API Token for development>
    env:
      dev: <The id of the guild for development>
      cache_policy: 3
      command_prefix: '$'
    ```
5. Run the jar file with `java -jar vcspeaker-kt-<version>.jar`
6. Invite the bot to your server.
7. Run `/vcspeaker settings channel:<TextChannel>` to set the text channel to be read aloud.
8. Now, when you send a message in the text channel, the bot will read it aloud in the voice channel.