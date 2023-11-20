import paho.mqtt.client as mqtt

# MQTT 설정
mqtt_broker = "localhost"
mqtt_port = 1883
mqtt_topic = "coord"


# 메시지를 처리하는 콜백 함수
def on_message(client, userdata, message):
    print(f"Received message: {message.payload.decode()}")


# MQTT 클라이언트 인스턴스 생성
consumer_client = mqtt.Client()

# 콜백 함수 설정
consumer_client.on_message = on_message

# MQTT 브로커와 연결
consumer_client.connect(mqtt_broker, mqtt_port, 60)

# 메시지 발행
consumer_client.subscribe(mqtt_topic)

# 메시지 수신 대기
consumer_client.loop_forever()
