import { useState, useEffect } from "react"


function App() {
  const [tasks, setTasks] = useState([])

  useEffect(() => {
  fetch("http://localhost:8080/tasks")
    .then(response => response.json())
    .then(data => {
      setTasks(data)
    })
  }, [])

  const [title, setTitle] = useState("")
  const [description, setDescription] = useState("")
  const [dueDate, setDueDate] = useState("")

async function toggleTask(task) {
  const updatedTask = {
    title: task.title,
    description: task.description,
    completed: !task.completed
  }

  const response = await fetch(`http://localhost:8080/tasks/${task.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(updatedTask)
  })

  const savedTask = await response.json()

  setTasks(tasks.map(currentTask =>
    currentTask.id === savedTask.id
      ? savedTask
      : currentTask
  ))
}

async function addTask(event) {
  event.preventDefault()

  const newTask = {
    title: title,
    description: description,
    dueDate: dueDate
  }

  const response = await fetch("http://localhost:8080/tasks", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(newTask)
  })

  const savedTask = await response.json()

  setTasks([...tasks, savedTask])

  setTitle("")
  setDescription("")
  setDueDate("")
}

  async function deleteTask(id) {
  await fetch(`http://localhost:8080/tasks/${id}`, {
    method: "DELETE"
  })

  setTasks(tasks.filter(task => task.id !== id))
}

  return (
    <div>
      <h1>Task Manager</h1>

      <form onSubmit={addTask}>
  <div>
    <label>Título</label>

    <input
      type="text"
      value={title}
      onChange={(event) => setTitle(event.target.value)}
    />
    </div>

  <div>
    <label>Descrição</label>

    <input
      type="text"
      value={description}
      onChange={(event) => setDescription(event.target.value)}
    />
  </div>

  <div>
  <label>Data de entrega</label>
    
  <input
    type="date"
    value={dueDate}
    onChange={(event) => setDueDate(event.target.value)}
    />
  </div>

  <button type="submit">
    Adicionar tarefa
  </button>
  </form>

      <h2>Minhas tarefas</h2>

      {tasks.map(task => (
        <div key={task.id}>
          <h3>{task.title}</h3>

          <p>{task.description}</p>

          <p>
            Tarefa criada em:{" "}
            {new Date(task.createdAt).toLocaleString("pt-BR")}
          </p>

          <p>
            Data de entrega:{" "}
            {task.dueDate
              ? task.dueDate.split("-").reverse().join("/")
              : "Não definida"}
          </p>

          <p>
            Status: {task.completed ? "Concluída" : "Pendente"}
          </p>

          <button onClick={() => toggleTask(task)}>
            Alterar status
          </button>

          <button onClick={() => deleteTask(task.id)}>
            Excluir
          </button>

          

        </div>
      ))}
    </div>
  )
}

export default App