 
import './App.css'; 
import Routing from './common/Routing';
import { useLocation } from 'react-router-dom';
import HeaderLogin from './component/HeaderLogin';
function App() {
   const location = useLocation();

  const showGuestHeader =
    location.pathname === '/login' ||
    location.pathname === '/register';

  return (
    <>
      {showGuestHeader && <HeaderLogin />}
      <Routing />
    </>
  );
}
export default App;
