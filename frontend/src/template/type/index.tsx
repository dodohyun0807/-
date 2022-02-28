import React, { useState, useCallback, Dispatch, SetStateAction, useEffect } from 'react';
import type { NextPage } from 'next';
import Router from 'next/router';
import Styled from './styled';

import { Text } from '@/components/atoms';
import { Title } from '@/components/molecules';
import { useChange } from '@/hooks';
import { TypeType } from '@/types/type';
import { createUserType } from 'action/userAction';
import { useDispatch } from 'react-redux';

const questions: TypeType[][] = [
  [
    { no: 0, constitution: '수족냉', description: '손발이 찬가' },
    { no: 1, constitution: '수족온', description: '손발이 따뜻한가' },
  ],
  [
    { no: 2, constitution: '오열', description: '더위를 싫어하는가' },
    { no: 3, constitution: '오한', description: '추위를 싫어하는가' },
  ],
  [
    { no: 4, constitution: '이차자', description: '이불을 차내고 자는가' },
    { no: 5, constitution: '이덮자', description: '이불을 엎고 자는가' },
  ],
  [
    { no: 6, constitution: '물많마', description: '물을 많이 마시는가' },
    { no: 7, constitution: '물적마', description: '물을 적게 마시는가' },
  ],
  [
    { no: 8, constitution: '찬음', description: '찬 음식을 좋아 하는가' },
    { no: 9, constitution: '뜨음', description: '뜨거운 음식을 좋아 하는가' },
  ],
];

const TypeTemplate: NextPage = () => {
  const dispatch = useDispatch();
  const QUESTIONS = questions.length;
  const [idx, setIdx] = useState(0);
  const [question, setQuestion] = useState(questions[idx]);
  const [value, setValue, onChange] = useChange('-1');
  const [answer, setAnswer] = useState([]);

  const skipFunction = useCallback(() => {
    Router.push('/signup/success');
  }, []);

  const prevFunction = useCallback(() => {
    setIdx(idx - 1);
    setQuestion(questions[idx - 1]);
  }, [value]);

  const nextFunction = useCallback(() => {
    if (answer[idx]) {
      answer.pop();
      setAnswer([...answer, value]);
    } else setAnswer([...answer, value]);

    setValue('-1');
    setIdx(idx + 1);
    if (idx + 1 === QUESTIONS) {
      dispatch(createUserType(answer));
      Router.push('/signup/success');
    } else setQuestion(questions[idx + 1]);
  }, [value]);

  return (
    <Styled.MainContainer>
      <Title value="TYPE" />
      <>
        {question.map((type) => {
          return (
            <Styled.CheckInputFormGroup
              key={type.no}
              value={type.no}
              type="radio"
              checked={Number(value) === type.no}
              name="type"
              onChange={onChange}
            >
              <Styled.Heading level={3}>
                <Text value={type.constitution} />
              </Styled.Heading>
              <Styled.Text value={type.description} />
            </Styled.CheckInputFormGroup>
          );
        })}
      </>
      <Styled.ButtonContainer>
        <Styled.Button onClick={prevFunction} disabled={idx === 0}>
          <Text value="PREV" />
        </Styled.Button>
        <Styled.Button onClick={skipFunction}>
          <Text value="SKIP" />
        </Styled.Button>
        <Styled.Button onClick={nextFunction} disabled={value === ''}>
          <Text value="NEXT" />
        </Styled.Button>
      </Styled.ButtonContainer>
    </Styled.MainContainer>
  );
};

export default TypeTemplate;
