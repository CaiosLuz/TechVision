# 🕶️ Marketplace TechVision com Leitor de Receitas Oftalmológicas

Este projeto é um **Marketplace de Óculos** desenvolvido com **Angular**, integrado a um sistema em **Python (FastAPI + Tesseract OCR)** para leitura de receitas oftalmológicas.  
O sistema permite **visualizar produtos, consultar detalhes e enviar imagens de receitas** para leitura automática dos graus oftalmológicos.

---

## 🚀 Funcionalidades

### 🛍️ Marketplace
- Catálogo de produtos com imagem, nome, preço e detalhes técnicos  
- Página de detalhes
- Layout responsivo e moderno com **Bootstrap**  
- Integração com **Supabase** para armazenamento de dados  

### Leitor de Receita (Backend em Python)
- Envio de imagem de receita oftalmológica  
- Leitura e retorno dos valores de **grau esférico, cilíndrico e eixo**  
- API criada em **FastAPI**, acessada diretamente pelo Angular  

> ⚠️ O projeto depende do backend em Python.  
> Para rodar o sistema completo, **inicie também a API de leitura de receitas** (instruções no repositório da IA).

---

## 🧰 Tecnologias Utilizadas

### Frontend
- 🅰️ **Angular 20+**
- 💅 **Bootstrap**
- ⚡ **TypeScript**
- 🌐 **Supabase**

### Backend (IA de leitura)
- 🐍 **Python 3.10+**
- ⚡ **FastAPI**
- 🔍 **Tesseract OCR**
- 🧠 **OpenCV**
- 🔣 **NumPy**

---

## 📦 Instalação e Execução

### 1️⃣ Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/marketplace-oculos.git
cd marketplace-oculos
```

---

### 2️⃣ Instalar o Angular CLI (caso ainda não tenha instalado)

```bash
npm install -g @angular/cli@20
```

### 3️⃣ Instalar Dependências do Angular

No diretório do projeto, execute o comando:

```bash
npm install
```

### 4️⃣ Rodar o projeto
```bash
ng serve
```
