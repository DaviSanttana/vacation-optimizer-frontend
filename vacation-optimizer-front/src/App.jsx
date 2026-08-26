import { useState } from 'react'
import './App.css'

function App() {
  const [vacationDays, setVacationDays] = useState('')
  const [month, setMonth] = useState('')
  const [year, setYear] = useState(2026)
  const [results, setResults] = useState([])
  const [loading, setLoading] = useState(false)
  const [darkMode, setDarkMode] = useState(true)

  const handleCalculate = async () => {
    setLoading(true)
    const response = await fetch('https://vacation-optimizer-backend-production.up.railway.app/api/vacations', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ 
        vacationDays: Number(vacationDays), 
        month, 
        year: Number(year) 
      })
    })
    const data = await response.json()
    setResults(data)
    setLoading(false)
  }

  const formatDate = (dateString) => {
    const [y, m, d] = dateString.split('-')
    return `${d}/${m}/${y}`
  }

  return (
    <div className={darkMode ? 'app dark' : 'app light'}>
      <button className="theme-toggle" onClick={() => setDarkMode(!darkMode)}>
        {darkMode ? '☀️' : '🌙'}
      </button>

      <header>
        <h1>Otimizador de Férias</h1>
        <p>Encontre a melhor data para tirar suas férias.</p>
      </header>

      <main>
        <aside className="form-panel">
          <div className="field">
            <label>Dias de férias disponíveis</label>
            <input
              type="number"
              value={vacationDays}
              onChange={(e) => setVacationDays(e.target.value)}
              min="1"
              max="30"
              placeholder="Ex: 10"
            />
          </div>

          <div className="field">
            <label>Mês que deseja</label>
            <select value={month} onChange={(e) => setMonth(e.target.value)}>
              <option value="">Selecione...</option>
              <option value="JANUARY">Janeiro</option>
              <option value="FEBRUARY">Fevereiro</option>
              <option value="MARCH">Março</option>
              <option value="APRIL">Abril</option>
              <option value="MAY">Maio</option>
              <option value="JUNE">Junho</option>
              <option value="JULY">Julho</option>
              <option value="AUGUST">Agosto</option>
              <option value="SEPTEMBER">Setembro</option>
              <option value="OCTOBER">Outubro</option>
              <option value="NOVEMBER">Novembro</option>
              <option value="DECEMBER">Dezembro</option>
            </select>
          </div>

          <div className="field">
            <label>Ano</label>
            <select value={year} onChange={(e) => setYear(e.target.value)}>
              <option value="2026">2026</option>
              <option value="2027">2027</option>
              <option value="2028">2028</option>
              <option value="2029">2029</option>
              <option value="2030">2030</option>
            </select>
          </div>

          <button className="calc-btn" onClick={handleCalculate}>
            {loading ? 'Calculando...' : 'Calcular Melhores Datas'}
          </button>
        </aside>

        <section className="results-panel">
          {results.length > 0 ? (
            <>
              <h2> Melhores opções encontradas</h2>
              <div className="grid">
                {results.slice(0, 8).map((option, index) => (
                  <div key={index} className="card">
                    <div className="card-title">Opção {index + 1}</div>
                    <div className="card-row">
                      <span className="card-label">Início</span>
                      <span className="card-value">{formatDate(option.startDate)}</span>
                    </div>
                    <div className="card-row">
                      <span className="card-label">Fim</span>
                      <span className="card-value">{formatDate(option.endDate)}</span>
                    </div>
                    <div className="card-divider" />
                    <div className="card-row">
                      <span className="card-label">Dias extras</span>
                      <span className="card-value highlight">{option.extraDaysGained}</span>
                    </div>
                    <div className="card-row">
                      <span className="card-label">Total</span>
                      <span className="card-value bold">{option.totalDaysOff} dias</span>
                    </div>
                  </div>
                ))}
              </div>
            </>
          ) : (
            <div className="empty">
              <p> Preencha o formulário para ver as melhores datas </p>
            </div>
          )}
        </section>
      </main>
    </div>
  )
}

export default App