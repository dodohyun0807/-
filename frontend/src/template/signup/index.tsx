import React, { useCallback } from 'react';
import type { NextPage } from 'next';
import Router from 'next/router';
import { useDispatch } from 'react-redux';
import Avatar from '@mui/material/Avatar';
import Person from '@mui/icons-material/Person';

import Styled from './styled';
import { Text, FooterContainer } from '@/components/atoms';
import { ImageUpload, Title } from '@/components/molecules';

import { useChange, useFileChange } from '@/hooks';
import { createUserProfile } from '@/action/userAction';

const SignupTemplate: NextPage = () => {
  const dispatch = useDispatch();
  const [value, , onChange] = useChange<HTMLInputElement>('');
  const [profileImg, , profileImgChange] = useFileChange(null);

  const nextFunction = useCallback(() => {
    dispatch(createUserProfile(profileImg[0], value));
    Router.push('/interest');
  }, [profileImg, value]);

  return (
    <Styled.MainContainer>
      <Title value="SIGN UP" />
      <Styled.ProfileContainer>
        <ImageUpload onChange={profileImgChange}>
          <Avatar
            src={
              profileImg.length > 0
                ? window.URL.createObjectURL(profileImg[0])
                : '/images/icon/blank_user.png'
            }
            sx={{ width: 150, height: 150 }}
          />
        </ImageUpload>
        <Styled.FormGroupWithIcon placeholder="nickname" value={value} onChange={onChange}>
          <Person />
        </Styled.FormGroupWithIcon>
      </Styled.ProfileContainer>
      <FooterContainer>
        <Styled.Button bgColor="#fff" onClick={nextFunction} disabled={!value}>
          <Text value="NEXT" />
        </Styled.Button>
      </FooterContainer>
    </Styled.MainContainer>
  );
};

export default SignupTemplate;
