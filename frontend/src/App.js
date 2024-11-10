import { ColorModeContext, useMode } from './theme';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { Routes, Route } from 'react-router-dom';
import TopBar from './pages/global/Topbar';
import Dashboard from './pages/dashboard';
import Sidebar from './pages/global/Sidebar';
import Students from './pages/students';
import Teachers from './pages/teachers';
import Courses from './pages/courses';
import Grades from './pages/grades';
import View from './pages/view';
import FAQ from './pages/faq';
import Calendar from './pages/calendar';
import CreateParent from './forms/create_parent';
import Login from './pages/login';
import SignUp from './pages/signup';
import StudentForm from './forms/StudentForm';
import CreateTeacher from './forms/create_teachers';
import CreateStudent from './forms/create_students';


function App() {
  const [theme, colorMode] = useMode();
  return (
    <ColorModeContext.Provider value={colorMode}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <div className="app">
          <Sidebar />
          <main className='content'>
            <TopBar />
            <Routes>
              <Route path="/" element={<Dashboard />} />
              <Route path="/students" element={<Students />} />
              <Route path="student/:studentId" element={<CreateStudent />} />
              <Route path="teachers" element={<Teachers />} />
              <Route path="create-teacher" element={<CreateTeacher />} />
              <Route path="courses" element={<Courses />} />
              <Route path="grades" element={<Grades />} />
              <Route path="view" element={<View />} />
              <Route path="faq" element={<FAQ />} />
              <Route path="calendar" element={<Calendar />} />
              <Route path="login" element={<Login />} />
              <Route path="signup" element={<SignUp />} />
              <Route path="create_parent" element={<CreateParent />} />
            </Routes>
          </main>
        </div>
      </ThemeProvider>
    </ColorModeContext.Provider>
  );
}

export default App;
