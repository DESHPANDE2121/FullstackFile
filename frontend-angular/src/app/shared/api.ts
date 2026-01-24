// Use relative URLs so the Angular dev-server proxy can forward to Spring Boot.
// This avoids CORS issues and also works better when deploying behind a reverse-proxy.
const BASE = '/api';

export const API = {
  auth: {
    signup: `${BASE}/auth/signup`,
    login: `${BASE}/auth/login`
  },
  employees: `${BASE}/employees`
};
