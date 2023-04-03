# SPRING BATCH (OLD)

대부분 5버전이 아닌 이전 버전을 사용하기 때문에 해당 프로젝트를 설정함. 

* Spring Batch 
1. Job (배치 실행 단위 )
 N개의 Step을 구성할 수 있고, Flow를 관리할 수 있음 
2. Step     
    - Chunk: 하나의 묶음을 나눠서 실행
    - Task: 하나의 작업기반으로 
3. Chunk
    - ItemReader, ItemProcessor, ItemWriter 등
    - ItemReader (대상 reading) -> ItemProcessor (filtering or 가공) -> ItemWriter (대상에 쓰기)
4. 실행구조 
    - parameter에 따라 JobInstance가 동일하게 또는 새로 생성되어 실행됨 
    - parameter가 동일해도 새로운 JobInstance를 생성하고 싶으면 RunIdIncrementer를 이용해 새로운 param을 주입 
5. JobParameter 
    - jobParameters.getString(key, defaultValue) 
    - @Value("#{jobParameter[Key]}")
    - -Dchunk=20
6. Scope
    - @JobScope, @StepScope 
    - bean을 생성/소멸 시킬 지 bean의 lifecycle 설정 
    - JobScope는 step에, StepScope는 tasklet, chunk에 선언 
    - @Value("#{jobParameter[Key]}")
    - thread safe하게 동작 
7. Reader
    - FlatFileItemReader 

 
```
// batch meta-data 
// https://docs.spring.io/spring-batch/docs/current/reference/html/schema-appendix.html

// Job이 실행되며 생성되는 최상의 계층 테이블
select * from BATCH_JOB_INSTANCE;

// job 실행 시작/종료 시간및 상태 
select * from BATCH_JOB_EXECUTION;

// Job 공유해야 할 데이터 직렬화 
select * from BATCH_JOB_EXECUTION_CONTEXT;

// Job 실행시 주입된 Param 
select * from BATCH_JOB_EXECUTION_PARAMS;
select * from BATCH_JOB_EXECUTION_SEQ;

select * from BATCH_JOB_SEQ;
select * from BATCH_STEP_EXECUTION;
select * from BATCH_STEP_EXECUTION_CONTEXT;
select * from BATCH_STEP_EXECUTION_SEQ;

```
field[ doNm	name	address	beerType	desc]
htps://github.com/resten1497/awesome-beer/blob/master/beer.csvt