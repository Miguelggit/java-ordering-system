# 🛒 Sistema de Pedidos

Sistema de pedidos desenvolvido em Java com Programação Orientada a Objetos pura, sem frameworks — contemplando processamento e análise de pedidos com operações de cálculo, filtragem, ordenação e rankings seguindo princípios de código limpo (DRY, KISS, SOLID) e arquitetura em camadas.

---

## 📦 Funcionalidades

- Cálculo do valor total de um pedido (preço × quantidade)
- Filtragem de itens por valor **máximo**
- Ordenação de itens por preço em ordem decrescente
- Adição de pedidos ao carrinho com **validação de estoque no construtor**
- **Desconto automático de estoque** ao adicionar pedido no carrinho
- Cálculo do total gasto por cliente usando `Map<Customer, Double>`
- Identificação do cliente que mais gastou com `Optional<Customer>`
- Identificação do produto mais vendido somando quantidades de todos os pedidos
- Ranking dos **top 3 clientes** que mais gastaram ordenados por valor decrescente
- Cálculo da **média de valor dos pedidos** no carrinho

---

## 🏗️ Estrutura do Projeto

```
src/
├── domain/
│   ├── Customer.java    # Cliente com id e nome
│   ├── Product.java     # Produto com preço e estoque
│   ├── Item.java        # Associação produto + quantidade com validação de estoque
│   ├── Order.java       # Pedido com lista de itens
│   └── Cart.java        # Carrinho agrupando pedidos por cliente
├── exception/
│   ├── StockProductException.java       # Estoque insuficiente
│   ├── EmptyCartException.java          # Carrinho vazio
│   ├── EmptyOrderException.java         # Pedido sem itens
│   ├── InvalidQuantityException.java    # Quantidade inválida
│   ├── InvalidPriceException.java       # Preço inválido
│   ├── NullCustomerException.java       # Cliente nulo
│   ├── NullProductException.java        # Produto nulo
│   └── DuplicateOrderException.java     # Pedido duplicado
├── services/
│   ├── OrderServices.java               # Lógica de negócio de pedidos
│   └── CartServices.java                # Lógica de negócio do carrinho
└── Main.java
```

---

## 🧩 Modelo de Domínio

| Classe      | Descrição |
|-------------|-----------||
| `Customer`  | Representa o cliente com `id` e `name`, usado como chave no `Map` do carrinho |
| `Product`   | Produto com `id`, `name`, `price` e `stock` |
| `Item`      | Associa um `Product` a uma `quantity` específica com **validação de estoque no construtor** |
| `Order`     | Pedido contendo uma lista de `Item` e pertencente a um `Customer` |
| `Cart`      | Gerencia pedidos usando `Map<Customer, List<Order>>` (apenas estrutura de dados) |

---

## 🗂️ Camada de Serviços

| Classe | Responsabilidade |
|--------|------------------|
| `OrderServices` | Operações de análise de pedidos individuais (cálculo total, filtragem, ordenação) |
| `CartServices` | Operações de carrinho e análise agregada (adicionar pedidos, totais por cliente, rankings, produto mais vendido) |

---

## ⚙️ Regras de Negócio

- Produtos com **estoque insuficiente** não podem ser adicionados — validação no construtor de `Item` lança `StockProductException`
- O estoque é **descontado automaticamente** ao adicionar o pedido no carrinho via `CartServices.addToCart()`
- Cliente é identificado pelo `id` no `equals/hashCode`, permitindo uso como chave no `Map`
- Pedidos com **mesmo ID** não podem ser adicionados duas vezes para o mesmo cliente — lança `DuplicateOrderException`
- Operações de análise em carrinho vazio lançam `EmptyCartException`
- Validações de integridade são feitas nos **construtores** (fail-fast pattern)
- **Separação de responsabilidades**: domínio apenas com dados e validações, lógica de negócio nos serviços (SRP)

---

## 🚨 Exceções Customizadas

| Exceção | Quando é lançada |
|---------|------------------|
| `StockProductException` | Quantidade solicitada excede o estoque disponível (validado no construtor de `Item`) |
| `EmptyCartException` | Operações de análise são chamadas em carrinho vazio |
| `EmptyOrderException` | Pedido é criado sem itens ou com lista vazia |
| `InvalidQuantityException` | Quantidade de item é zero ou negativa |
| `InvalidPriceException` | Preço de produto é zero ou negativo |
| `NullCustomerException` | Pedido é criado sem cliente associado |
| `NullProductException` | Item é criado sem produto associado |
| `DuplicateOrderException` | Tentativa de adicionar pedido com ID duplicado para o mesmo cliente via `CartServices` |

---

## ▶️ Como Executar

1. Clone o repositório
   ```bash
   git clone https://github.com/<seu-usuario>/SistemaDePedidos.git
   ```
2. Compile e execute
   ```bash
   javac src/Main.java
   java -cp src Main
   ```

---

## 🛠️ Tecnologias Utilizadas

- Java (POO pura, sem frameworks)
- Collections (`Map`, `List`, `Optional`)
- Exception handling customizado
- Arquitetura em camadas (Domain + Services)

---

## 📌 Roadmap / Próximos Passos

### Level 1 — Core ✅
- [x] Calcular total de um pedido
- [x] Filtrar pedidos por valor mínimo
- [x] Ordenar pedidos por valor

### Level 2 — Intermediate ✅
- [x] Total gasto por cliente
- [x] Cliente que mais gastou

### Level 3 — Advanced ✅
- [x] Produto mais vendido
- [x] Top 3 clientes que mais gastaram
- [x] Média de valor dos pedidos

### Phase 2 — Exception Handling ✅
- [x] 8 exceções customizadas implementadas
- [x] Validações aplicadas nos construtores
- [x] Fail-fast pattern implementado
- [x] Validação de estoque movida para construtor de `Item`

### Phase 3 — Architectural Refactoring ✅
- [x] Criar `OrderServices` com lógica de pedidos
- [x] Criar `CartServices` com lógica de carrinho
- [x] Limpar classes de domínio (apenas dados + validações)
- [x] Aplicar SRP separando domínio de lógica de negócio
- [x] Corrigir nomenclaturas (Order.items, Cart.orders)

### Phase 4 — Streams Refactoring ⏳
- [ ] Refatorar `OrderServices` com Java Streams
- [ ] Refatorar `CartServices` com Java Streams e API funcional
- [ ] Simplificar loops e operações de agregação
