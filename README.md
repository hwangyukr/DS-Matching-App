#  협동분산시스템 3팀 - 매칭 시스템   

## 팀원   
201713064 신지우    
201713080 정하영    
201411235 임환규    
201714152 박종현    
201714157 이종완    

클라이언트
```

```

서버
```

```

# 협분산 팀플

[쿼리](https://www.notion.so/fe278c2376034f33884578d562d3c541)

## 토큰(로그인) 필요없는 resources

```java
cmClientStub.loginCM("dummy", "0000"); 
```

- 서버로 reply받으려면 위 코드 처럼 더미아이디로 로그인 필요

[SIGN-UP](https://www.notion.so/SIGN-UP-255d2269855542398e3299cb969a9b82)

[SIGN-IN](https://www.notion.so/SIGN-IN-bb6443849e374427b220916b6886d051)

## 토큰(로그인) 필요한 resources

```java
ue.setEventField(CMInfo.CM_STR, "token", token);
```

- SIGN-IN으로 로그인 하면, token을 리턴 값을 받는데 아래 항목들을 접근하려 할 때 위 코드와 같이 토큰을 항상 넣어줘야 합니다.

### 토큰을 입력안한 경우 서버의 응답

```java
ue.setEventField(CMInfo.CM_INT, "success", "0");
ue.setEventField(CMInfo.CM_STR, "msg", "NOT AUTHORIZED");
cmServerStub.send(ue, ue.getSender());
```

### 토큰이 만료된 경우 서버의 응답

```java
ue.setEventField(CMInfo.CM_INT, "success", "0");
ue.setEventField(CMInfo.CM_STR, "msg", "토큰 시간 만료");
cmServerStub.send(ue, ue.getSender());
```

- 가끔 인터넷 돌아다니다 보면 로그인이 만료될 때가 있는 거 처럼 , 토큰 시간이 만료가 있습니다. 클라이언트 쪽에서 이 에러를 받으면, 자동 로그아웃 처리를 해서 로그인으로 리다이렉트해야함

[GET-TEAMS](https://www.notion.so/GET-TEAMS-187461d86e514de7b299c0a9fb998905)

[GET-TEAM](https://www.notion.so/GET-TEAM-7dee8b0540a04032b1a3d207eb4acde8)

[CREATE-TEAM](https://www.notion.so/CREATE-TEAM-5c808414613a41a4811356395602203d)

[GET-APPLICATIONS](https://www.notion.so/GET-APPLICATIONS-3a3869e6a69c4855b4220056131111c5)

[APPLY-TEAM](https://www.notion.so/APPLY-TEAM-47fa075c89fd4866acb867ce0b11bc23)

[PROCESS-APPLICATION](https://www.notion.so/PROCESS-APPLICATION-5a23fcae51554b41bfdb83ff220972d0)

[GET-PROFILE](https://www.notion.so/GET-PROFILE-e1cc795ce7774c988de55ee8c2c39944)

[POST-PROFILE](https://www.notion.so/POST-PROFILE-338b303c36a9472cbb5c4bddf1b7dbe4)

[PUT-PROFILE](https://www.notion.so/PUT-PROFILE-5d534b8f86614ae4bd2f528b8a27ce01)

[DELETE-PROFILE](https://www.notion.so/DELETE-PROFILE-d0529822dd3144f19a78a391ceb2f012)

[GET-BOARDS](https://www.notion.so/GET-BOARDS-a0e5165e380746fabbda03f80525c268)

[GET-BOARD](https://www.notion.so/GET-BOARD-10b26827444f41f69eef816ce100d1ee)

[POST-BOARD](https://www.notion.so/POST-BOARD-836507df089c4f0a8a9eac05804ed1de)

[PUT-BOARD](https://www.notion.so/PUT-BOARD-030c46379c924e34b1d14172fe36a28d)

[DELETE-BOARD](https://www.notion.so/DELETE-BOARD-9c14e430a21147c3b572cc902ebbe227)
dddd
