import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  // Adicione esta configuração esbuild
  esbuild: {
    loader: 'jsx',
    include: /src\/.*\.jsx?$/, // Inclui arquivos .js e .jsx na pasta src
    exclude: [],
  },
  // Opcional: Otimização para incluir .js como JSX também
  optimizeDeps: {
    esbuildOptions: {
      loader: {
        '.js': 'jsx',
      },
    },
  },
})