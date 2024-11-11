const express = require('express');
const cors = require('cors');
const app = express();

app.use(cors({
origin: 'http://localhost:3000'
}));

app.get('/tu-endpoint', (req, res) => {
res.send({ mensaje: 'Hola desde el backend' });
});

app.listen(8080, () => {
console.log('Servidor backend ejecutándose en http://localhost:8080');
});
