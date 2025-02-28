terraform {
  // aws 라이브러리 불러옴
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }
}

# AWS 설정 시작
provider "aws" {
  region = var.region
}
# AWS 설정 끝

# 다이나모 DB 시작

## Post 테이블 생성
resource "aws_dynamodb_table" "dynamodb_table_post" {
  name           = "post"
  billing_mode   = "PROVISIONED"
  read_capacity  = 5
  write_capacity = 5
  hash_key       = "id"

  attribute {
    name = "id"
    type = "S"
  }
}

## 채팅 메세지 테이블 생성
resource "aws_dynamodb_table" "dynamodb_table_chatMessage" {
  name           = "chatMessage"
  billing_mode   = "PROVISIONED"
  read_capacity  = 5
  write_capacity = 5
  hash_key       = "chatRoomId"
  range_key      = "createDate"

  attribute {
    name = "chatRoomId"
    type = "N"
  }

  attribute {
    name = "createDate"
    type = "S"
  }
}

# 다이나모 DB 끝