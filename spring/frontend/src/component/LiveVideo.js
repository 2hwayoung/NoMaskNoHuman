import React, { useEffect } from 'react';

const LiveVideo = ({ history }) => {

  const goHome = () => {
    history.push('/');
  };

  useEffect(() => {
    console.log(history);
  }, [history]);

  return (
    <div>
      <h1>소개</h1>
      <p>이 프로젝트는 리액트 라우터 기초를 실습해보는 예제 프로젝트랍니다.</p>
      <button onClick={goHome}>홈으로</button>
    </div>
  );
};

export default LiveVideo;