from kafka import KafkaConsumer
import base64

# Kafka 설정
kafka_server = "43.201.20.124:9092"  # Kafka 서버 주소
kafka_topic = "coffee"  # Kafka 토픽 이름

# Kafka 컨슈머 인스턴스 생성
consumer = KafkaConsumer(
    kafka_topic,
    bootstrap_servers=[kafka_server],
    auto_offset_reset="latest",  # 가장 오래된 메시지부터 읽기 시작
    group_id="coffee-tree",  # 컨슈머 그룹 ID
)

# 메시지 읽기
print(f"Listening for messages on topic '{kafka_topic}'...")
for message in consumer:
    try:
        # base64로 인코딩된 메시지를 디코딩
        decoded_message = base64.b64decode(message.value).decode("utf-8")
        print(f"Received message: {decoded_message}")
    except Exception as e:
        print(f"Error decoding message: {e}")
