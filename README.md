# Audio Generator Service (Part of the NURA Project)

The **Audio Generator Service** is a core component of the **NURA Project**, an automated system for generating catchy YouTube clips from trending topics. This service is responsible for downloading audio from YouTube videos, splitting the audio into manageable chunks, and preparing messages for further processing in the transcription and categorization pipelines.

## Overview

This microservice:

1. Downloads audio from YouTube videos based on video IDs.
2. Splits the audio into 30-second chunks.
3. Sends the chunks and metadata to RabbitMQ for processing by the transcription service.

This service plays a vital role in ensuring accurate audio chunking, enabling the transcription and categorization services to perform effectively in the NURA ecosystem.

---

## Usage

### Prerequisites

- **Java 17+** installed.
- **RabbitMQ server** running and configured with the specified exchange, queue, and binding:
    - **Exchange Name:** `audio_exchange`
    - **Queue Name:** `audio_queue`
    - **Routing Key:** `audio.routing.key`
- **yt-dlp** installed on the host machine. You can install it with:
  ```bash
  pip install yt-dlp

---

## RabbitMQ Integration

The service uses RabbitMQ to send audio chunk messages to the `transcription_service`.

### Example Message

#### Java Representation
```java
package com.gabriel.audio_generator_service.application.messaging;

import java.io.Serializable;
import java.util.List;

public record AudioChunkMessage(
    String channelId, 
    String videoId, 
    Object audioChunk, 
    List<String> tags, 
    String category
) implements Serializable {}
```

---

## How It Fits Into the NURA Project

The Audio Generator Service works as part of the larger NURA Project ecosystem, which includes:

- Trend Analysis Service: Identifies relevant trending topics.
- Audio Generator Service: Downloads and processes YouTube audio into chunks.
- Transcription Service: Converts audio chunks to text.
- Summarization Service: Summarizes transcribed text.
- Categorization Service: Matches summaries with pre-defined categories.
- Clip Generator Service: Creates video clips based on categorized content.
- Uploader Service: Uploads clips with automatically generated titles and thumbnails.
- This interconnected system enables the seamless generation and publication of highly targeted and engaging YouTube content.

For further details, [![NURA Project](https://img.shields.io/badge/NURA-Project-blue.svg)](https://github.com/gabriel-dears/nura)

