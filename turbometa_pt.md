# TurboMeta
## Proposta de Solução Técnica

**Tipo de documento:** Proposta de Arquitetura Técnica e Implementação  
**Projeto:** Aplicativo Web TurboMeta  
**Status:** Solução Proposta  
**Arquitetura:** Monólito Modular

---

## 1. Resumo Executivo

O TurboMeta é uma aplicação web leve desenvolvida para oferecer uma plataforma centralizada para gerenciar e pesquisar informações sobre turbocompressores.

A aplicação fornecerá duas áreas distintas:

- **Backoffice:** Interface administrativa para gerenciar usuários e registros de turbocompressores.
- **Frontoffice:** Interface voltada para o usuário, permitindo que usuários autenticados pesquisem e consultem informações sobre turbocompressores.

A solução proposta prioriza simplicidade, mantenabilidade, segurança e facilidade de implantação.

A aplicação será desenvolvida em Java e Spring Boot, com renderização de HTML no servidor e SQLite como banco de dados.

A arquitetura evita deliberadamente infraestrutura e complexidade tecnológica desnecessárias. Toda a solução será entregue como uma única aplicação implantável.

## 2. Objetivos do Projeto

Os principais objetivos são:

1. Fornecer autenticação segura de usuários.
2. Implementar controle de acesso por perfil.
3. Permitir que administradores gerenciem usuários da aplicação.
4. Permitir que administradores gerenciem registros de turbocompressores.
5. Oferecer uma interface de busca intuitiva para usuários autenticados.
6. Garantir usabilidade responsiva em desktops e dispositivos móveis.
7. Manter o mínimo possível de requisitos de implantação e operação.

## 3. Stack Tecnológica Proposta

As seguintes tecnologias são propostas:

| Componente | Tecnologia | Finalidade |
|---|---|---|
| Linguagem de Programação | Java 25 | Desenvolvimento da aplicação |
| Framework do Backend | Spring Boot 4.x | Framework da aplicação |
| Camada Web | Spring MVC | Tratamento de requisições HTTP |
| Frontend | Thymeleaf | Renderização de HTML no servidor |
| Framework de UI | Bootstrap 5 | Interface responsiva |
| Banco de Dados | SQLite | Persistência relacional |
| Acesso a Dados | Spring JDBC | Operações no banco de dados |
| Autenticação | Spring Security | Autenticação e autorização |
| Hash de Senhas | BCrypt / Argon2 | Armazenamento seguro de senhas |
| Migrações do Banco | Flyway | Gerenciamento do esquema do banco |
| Ferramenta de Build | Maven | Gerenciamento de dependências e build |
| Implantação | Docker | Empacotamento e implantação da aplicação |

### Justificativa da Seleção de Tecnologia

**Spring Boot**

Fornece um framework maduro para aplicação, injeção de dependências, gerenciamento de configuração, validação e integração de segurança.

**Thymeleaf + Bootstrap**

Oferece uma abordagem direta para construir interfaces web responsivas sem introduzir a complexidade de um framework frontend JavaScript separado.

**SQLite**

É um banco de dados relacional leve que não exige um servidor de banco de dados dedicado. Os dados são armazenados em um único arquivo persistente.

**Spring Security**

Fornece mecanismos estabelecidos de autenticação e autorização, incluindo gerenciamento de sessões e controle de acesso por perfil.

**Spring JDBC**

Fornece acesso direto e leve ao banco de dados, sem exigir uma camada ORM complexa. Isso é particularmente adequado para o modelo de dados relativamente simples e a integração com SQLite.

## 4. Arquitetura da Solução

A arquitetura proposta segue a abordagem de **Monólito Modular**.

Todos os componentes da aplicação serão executados dentro de uma única aplicação Spring Boot e compartilharão o mesmo ciclo de implantação.

### 4.1 Arquitetura Lógica

A aplicação será composta pelas seguintes camadas:

**Camada de Apresentação**
- Templates Thymeleaf.
- Componentes Bootstrap.
- Formulários e tabelas HTML.
- Integração opcional com HTMX para interações dinâmicas de busca.

**Camada de Aplicação**
- Controladores Spring MVC.
- Serviços da aplicação.
- Validação de entrada.
- Regras de negócio e verificações de autorização.

**Camada de Segurança**
- Spring Security.
- Autenticação baseada em sessão.
- Controle de acesso com base nos privilégios do usuário.
- Hash de senhas.

**Camada de Persistência**
- Spring JDBC.
- Componentes de repositório.
- Banco de dados SQLite.
- Migrações do banco com Flyway.

### 4.2 Módulos da Aplicação

A aplicação será organizada em dois módulos funcionais principais.

**Módulo de Gestão de Usuários**
- Autenticação de usuários.
- Administração de usuários.
- Ativação e desativação de contas.
- Autorização de usuários.

**Módulo de Gestão de Turbocompressores**
- Cadastro de turbocompressores.
- Edição de turbocompressores.
- Exclusão de turbocompressores.
- Busca de turbocompressores.

## 5. Design do Banco de Dados

A aplicação usará inicialmente SQLite com duas tabelas principais.

### 5.1 Tabela de Usuários

Tabela: `app_user`

| Campo | Tipo de Dado | Restrições |
|---|---|---|
| id | INTEGER | Chave primária, auto incremento |
| username | VARCHAR(30) | NOT NULL |
| email | TEXT | UNIQUE, NOT NULL |
| password_hash | TEXT | NOT NULL |
| active | BOOLEAN | NOT NULL |
| is_admin | BOOLEAN | NOT NULL |

**Regras de Negócio:**
- Endereços de e-mail devem ser válidos e únicos.
- As senhas devem ser hashadas com segurança antes da persistência.
- Usuários inativos não poderão se autenticar.
- Administradores têm acesso ao backoffice.
- Usuários comuns têm acesso ao frontoffice.

### 5.2 Tabela de Turbocompressores

Tabela: `turbo`

| Campo | Tipo de Dado | Restrições |
|---|---|---|
| ref | TEXT | Chave primária |
| brand | TEXT | NOT NULL |
| model | TEXT | NOT NULL |
| geo | TEXT | Opcional |

**Regras de Negócio:**
- Cada turbocompressor deve ter uma referência única.
- Marca e modelo são obrigatórios.
- Informações geográficas são opcionais.
- Registros de turbocompressores só podem ser modificados por administradores.

### 5.3 Considerações sobre o Banco de Dados

O banco de dados será armazenado em um arquivo persistente fora do contêiner da aplicação.

O Flyway será usado para versionar e gerenciar alterações no esquema do banco, garantindo inicialização e atualização consistentes.

## 6. Requisitos Funcionais

### 6.1 Autenticação

A página inicial da aplicação será a tela de login.

**Recursos:**
- Branding e logotipo do TurboMeta.
- Autenticação por e-mail e senha.
- Validação de entradas.
- Feedback para credenciais inválidas.
- Funcionalidade de logout.
- Gerenciamento de sessão.

Após a autenticação bem-sucedida, os usuários serão redirecionados de acordo com suas permissões:

- Administradores → Backoffice.
- Usuários comuns → Frontoffice.

### 6.2 Backoffice – Administração

Apenas usuários autenticados com privilégios de administrador (`is_admin = true`) podem acessar o backoffice.

#### 6.2.1 Gestão de Usuários

Os administradores poderão:

| Operação | Descrição |
|---|---|
| Pesquisar | Buscar usuários por nome de usuário ou e-mail |
| Inserir | Criar novos usuários da aplicação |
| Editar | Atualizar informações do usuário e permissões |
| Remover | Excluir registros de usuários |
| Ativar/Desativar | Habilitar ou desabilitar contas de usuário |

Considerações adicionais:
- As senhas serão hashadas antes do armazenamento.
- A unicidade de e-mail será reforçada.
- Os administradores poderão conceder ou revogar privilégios administrativos.
- A aplicação deve prevenir exclusão ou desativação acidental do último administrador ativo.

#### 6.2.2 Gestão de Turbocompressores

Os administradores poderão:

| Operação | Descrição |
|---|---|
| Pesquisar | Buscar por referência, marca ou modelo |
| Inserir | Registrar um novo turbocompressor |
| Editar | Atualizar informações de um turbocompressor existente |
| Remover | Excluir registros de turbocompressores |

Os resultados da pesquisa serão exibidos em uma tabela estruturada e paginada.

### 6.3 Frontoffice – Busca de Turbocompressores

Usuários autenticados não administrativos terão acesso à funcionalidade de busca de turbocompressores.

**Funcionalidade disponível:**
- Busca por referência.
- Busca por marca.
- Busca por modelo.
- Exibição de registros de turbocompressores correspondentes.
- Visualização dos detalhes disponíveis do turbocompressor.

O frontoffice será somente leitura. Os usuários não terão permissão para criar, modificar ou excluir registros.

## 7. Interface do Usuário

A interface seguirá um design limpo, moderno e responsivo.

### 7.1 Página de Login

- Logotipo e branding do TurboMeta.
- Campos de e-mail e senha.
- Ação de login.
- Mensagens de erro de autenticação.

### 7.2 Backoffice

Navegação proposta:

- Dashboard.
- Usuários.
- Turbos.
- Logout.

As telas de administração fornecerão:
- Busca e filtros.
- Apresentação tabular de dados.
- Formulários de criação e edição.
- Confirmação antes da exclusão.
- Mensagens claras de validação e erro.

### 7.3 Frontoffice

A tela principal focará na busca de turbocompressores.

Os usuários serão apresentados com:
- Campo de busca.
- Filtros de busca.
- Resultados da busca.
- Referência, marca, modelo e informações geográficas do turbocompressor.

## 8. Considerações de Segurança

A segurança será implementada usando Spring Security.

As seguintes medidas serão incluídas:

1. Hash de senha usando BCrypt ou Argon2.
2. Autenticação baseada em sessão.
3. Autorização por perfil.
4. Proteção de endpoints administrativos.
5. Proteção CSRF para envio de formulários.
6. Validação de entrada no servidor.
7. Proteção contra injeção SQL por meio de consultas parametrizadas.
8. Encerramento seguro de sessão no logout.
9. Restrição de acesso para contas inativas.
10. Prevenção de escalonamento de privilégios não autorizado.

A autorização será sempre aplicada no servidor, independentemente da interface apresentada ao usuário.

## 9. Arquitetura de Implantação

A aplicação será empacotada e implantada como um contêiner Docker.

### 9.1 Modelo de Implantação

A implantação proposta consiste em:

- Uma aplicação Spring Boot.
- Um contêiner Docker.
- Um arquivo de banco de dados SQLite persistente.
- Proxy reverso opcional para término de HTTPS.

### 9.2 Persistência

O banco de dados SQLite será montado usando um volume Docker ou um diretório persistente do host.

Isso garante que a reinstalação da aplicação ou a substituição do contêiner não resulte em perda de dados do banco.

### 9.3 Vantagens da Implantação

- Processo de implantação simples.
- Requisitos mínimos de infraestrutura.
- Nenhum servidor de banco de dados dedicado.
- Baixo esforço operacional.
- Backup e restauração fáceis.
- Atualizações simples da aplicação.

## 10. Requisitos Não Funcionais

| Categoria | Requisito |
|---|---|
| Manutenibilidade | Componentes da aplicação modulares e claramente separados |
| Segurança | Autenticação e autorização aplicadas no servidor |
| Desempenho | Consultas eficientes ao banco e resultados de busca paginados |
| Disponibilidade | Disponibilidade da aplicação dependente da infraestrutura de hospedagem |
| Usabilidade | Interface responsiva e intuitiva |
| Portabilidade | Implantação baseada em Docker |
| Escalabilidade | Arquitetura permite migração futura para PostgreSQL |
| Integridade dos Dados | Restrições do banco e validação em nível de aplicação |

O SQLite é apropriado para a carga inicial esperada, especialmente quando a concorrência de gravação é limitada. Se o uso da aplicação ou os requisitos de gravação concorrente aumentarem significativamente, o PostgreSQL pode ser introduzido como plataforma de persistência alternativa.

## 11. Escopo do Projeto

### Incluído na Entrega Inicial

- Página de login do TurboMeta e branding.
- Autenticação de usuários e gerenciamento de sessão.
- Administração de usuários (CRUD).
- Administração de turbocompressores (CRUD).
- Funcionalidade de busca de turbocompressores.
- Controle de acesso por perfil.
- Interface responsiva.
- Integração com banco de dados SQLite.
- Migrações do banco.
- Configuração de empacotamento e implantação em Docker.

### Possíveis Melhorias Futuras

A arquitetura permitirá a introdução de funcionalidades adicionais sem exigir um redesenho fundamental.

Possíveis melhorias incluem:

- Recuperação de senha.
- Fluxos de cadastro de usuários.
- Importação/exportação de registros de turbocompressores (CSV/Excel).
- Filtros avançados de busca.
- Imagens e anexos de turbocompressores.
- Logs de auditoria e rastreamento de atividades administrativas.
- Perfis e permissões adicionais de usuários.
- Integração de API com sistemas externos.
- Migração para PostgreSQL.

Essas melhorias não fazem parte do escopo inicial, a menos que sejam explicitamente acordadas.

## 12. Entregáveis Propostos

A entrega do projeto incluirá:

1. Código-fonte completo da aplicação.
2. Configuração do projeto Maven.
3. Esquema do banco SQLite e migrações do Flyway.
4. Implementação de autenticação e autorização.
5. Gestão de usuários no backoffice.
6. Gestão de turbocompressores no backoffice.
7. Busca de turbocompressores no frontoffice.
8. Interface responsiva.
9. Dockerfile e configuração de implantação.
10. Documentação técnica cobrindo configuração, implantação e manutenção.

## 13. Conclusão

A solução proposta para o TurboMeta oferece uma abordagem pragmática e sustentável para entregar a funcionalidade necessária.

Ao combinar Java, Spring Boot, Thymeleaf, Bootstrap e SQLite, a aplicação pode fornecer todas as capacidades necessárias sem introduzir complexidade arquitetural ou operacional desnecessária.

A abordagem de Monólito Modular oferece um equilíbrio entre separação clara de responsabilidades, facilidade de desenvolvimento e implantação direta.

A solução foi desenhada para atender aos requisitos atuais, mantendo a flexibilidade para evoluir conforme surgirem necessidades futuras do negócio.
