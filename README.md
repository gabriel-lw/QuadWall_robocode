
## Detalhes do Projeto

**Curso:** IFSC - Análise e Desenvolvimento de Sistemas (Primeira Fase)

**Matéria:** ICO7862 - Introdução a Computação (2025-2)

**Professor:** Diego da Silva de Medeiros

**Estudantes:** Angelo, Gabriel Luz, Vinicius

## Relatório

### 1. Introdução	

Robocode é um jogo virtual à base de linguagem Java onde o objetivo é programar um tanque de guerra digital para enfrentar outros em um campo de batalha 2D. Nesse sentido, ele foi usado como objeto de estudo com o objetivo de desenvolver habilidades de programação e controle de versão através do Git, ferramenta de grande importância para o desenvolvimento de software.

Através do Git, é possível desenvolver o código para o objetivo desejado de forma linear ou, mais comumente, através de diferentes “linhas de código” chamadas branches. Por meio delas, é possível a divisão de um grupo para diferentes propósitos, buscando dinamizar o processo e, depois de finalizados, unirem as diferentes branches através de “merges” para obter-se uma versão mais sofisticada e, eventualmente, a versão final.

### 2. Objetivos da Atividade

A atividade tem com principais objetivos o aprendizado do uso da ferramenta Git para o controle de versão, através do uso de diferentes branches, repositórios locais e remotos (via Github) e merges das diferentes linhas de código desenvolvidas. Além disso, dado que o Robocode utiliza linguagem Java, também é de interesse, durante o exercício da atividade, de aprender mais sobre a estrutura e comandos usados em Java.

Ademais, também desenvolve-se habilidades e comportamento no que tange o trabalho em equipe no âmbito da programação, como divisão de tarefas, comunicação do conteúdo de cada branch, permissionamento para realizar “pushes” e outros. Nessa perspectiva, a atividade busca trazer uma experiência próxima do trabalho em empresas focadas ou que fazem uso, de alguma forma da programação.

### 3. Descrição da Atividade

O processo iniciou com a definição das características e comportamento do robô  em sala de aula averiguando o que achávamos uma boa estratégia e ao mesmo tempo o que conseguiríamos fazer no prazo estabelecido.

Após isso, com uma ideia geral do robô em mente, começamos a separar sua construção em partes menores que poderiam ser implementadas de forma isolada por cada membro e posteriormente integradas ao robô com as features dos demais colaboradores.

A escolha de quem ficaria responsável por cada parte da implementação do robô se deu por um grupo no WhatsApp onde discutimos a organização do projeto. 

Cada participante desenvolvia sua feature e ficava responsável pela correção de conflitos com a branch Main do repositório remoto do robô no github  que possuía as implementações finalizadas. Se estivesse tudo certo com o commit e nem um trabalho extra fosse necessário naquela branch, era dado o merge na Main, deixando disponível no github a implementação mais recente da feature para os demais colaboradores que por sua vez a integravam em seu código e seguiam o desenvolvimento das demais features.

### 4. Estrutura do Git Utilizada

* Repositório: O repositório foi criado no github de um dos participantes do grupo (gabriel-lw) para conter os arquivos .java e .class do robô.

* Branches: Durante o trabalho foram criadas várias branches para  o desenvolvimento das features, tais quais:

  * irParaBorda: calcula a borda mais próxima do ponto de spawn do robô no início da batalha e se desloca até ela. Ação necessária para começar as trajetórias pelas bordas dos quadrantes

  * MoveQuad: feito a base para o sistema de movimentação em quadrante, como a definição dos pontos de trilho da movimentação,ainda limitado a se movimentar no quadrante mais próximo do surgimento, sem troca de quadrantes. Além da definição de algumas variáveis importantes

  * Tiro: responsável pela feature de escalar o poder do tiro conforme a proximidade dos alvos e decisão das condições para abrir fogo.

  * EscanearQuad: branch responsável pela funcionalidade de reconhecimento do campo e coleta de informações das posições dos inimigos para uma lista referente a cada quadrante. Além disso, um pequeno ajuste na mira do robô para sempre apontar para dentro em relação a parede de quadrante que está percorrendo. Por fim, foi feita a adequação final para AdvancedRobot para refletir as mudanças de direção do projeto.

  * ChangeQuad: melhoria no sistema de movimentação em quadrante, agora possuindo uma feature de calcular o deslocamento para qualquer quadrante desejado(informação adquirida por algum gatilho) e com isso feito um sistema passando por diferentes quadrantes . As posições dos trilhos agora são salvas em um array multidimensional para comportar o X e Y de cada uma das 3 posições de quadrante, entre 4 quadrantes, isso tudo com 3 camadas de proximidade do centro(não houve tempo para implementar mudança de camadas)

  * Colisão: resposta à colisão com inimigos, se nosso robô iniciou a colisão, faz apontar a arma e o radar para frente enquanto se afasta do inimigo. Se não foi nosso robô quem iniciou, apenas rotaciona o radar.

  * BranchVariada: feito a feature do radar buscar em uma área específica de acordo com sua posição de quadrante; enfoque do radar e da arma em um inimigo ao escanea-lo(quando estiver a uma certa proximidade); estabelecido limites de ângulos (para os casos de borda 1, para não perder tempo escaneando a parede do quadrante); adequação do restante do código para AdvancedRobot; pequenas alterações na feature de tiro; correção de pequenos bugs e refatorações de código. 

* Commits: os commits  são feitos para salvar o progresso no desenvolvimento, permitindo por exemplo a rastreabilidade de erros. No projeto usamos os commits para salvar nossas modificações no robô, que eram primeiro enviadas para branches separadas, assim era salvo o progresso sem alterar o código principal antes de termos certeza sobre a implementação da feature da branch. As mensagens dos commits quando são claras e objetivas permitem que rapidamente seja entendido quais alterações foram feitas, o que facilita a análise do código e contribui para a organização geral do projeto.

* Pull requests: é uma solicitação para integrar suas alterações ao ramo principal do projeto. Está solicitação precisa ser aprovada por outros membros de equipe e fica disponível para discussão, testes, revisão e etc, tudo para garantir a qualidade do código antes de integrá-lo. No caso do nosso projeto, usávamos principalmente para deixar o código finalizado disponível para análise, mas as discussões aconteciam principalmente no grupo do WhatsApp pela maior praticidade.

### 5. Resultados e Aprendizados
	
Durante o desenvolvimento do projeto e através da nossa colaboração, aprendemos a usar as tecnologias do git, Github e Java, além de ganharmos experiência em como desenvolver um projeto em grupo. 

Para entrar em maior detalhe, aprendemos a utilizar o git e o Github para fazer o controle de versão do nosso projeto. Por exemplo, aprendemos: a criação de commits com mensagens, para um maior conhecimento de que cada mudança no projeto faz, a utilização de merges entre diferentes branches, em situações como a de atualizar uma branch quando uma mudança no Main foi feita, e merge pull requests, se um merge não seria o mais apropriado, como em situações onde estamos adicionando uma nova funcionalidade à branch principal.

Quanto vem ao Java e a programação do robô, aprendemos a criar e chamar métodos de tipo “void” para o robô fazer certas instruções quando esse método é chamado, sem precisar repetir código, aprendemos a utilizar manifestações de Arrays para implementar certas funcionalidades do robô, como ArrayList no escaneamento dos nomes dos robôs, para não escanear a localização de um robô já escaneado, e Arrays multidimensionais para a criação de trilhos no campo de batalha, que são usados pelo robô para se movimentar.

A maioria dos desafios enfrentados foram encontrados no processo de programação do robô, na implementação de funcionalidades e ideias que tínhamos.  Por exemplo, desafios foram encontrados na implementação do escaneamento da localização  de robôs oponentes. Estes desafios, além de outros de funcionalidades diferentes, foram superados através do estudo e uso de novas tecnologias do Java, como o ArrayList que foi usado para registrar os nomes de robôs escaneados em um certo período de tempo, para não escanear o mesmo robô e sua localização duas vezes. 

Os resultados obtidos neste projeto foram um robô que possui essas funcionalidades: movimento pelos quadrantes de um campo de batalha, que são criados na divisão da altura e largura do campo pela metade, uma funcionalidade que o move para o quadrante mais próximo de si quando a batalha começa, um sistema de determinação da localização e quadrante ocupado de cada robô escaneado, que leva a indicação de qual quadrante do mapa está mais populado a cada escaneamento inteiro do campo. Além disso, o robô consegue: manter o seu radar e arma apontados continuamente para um robô escaneado no mesmo quadrante, e mover para trás no caso de um robô entrar em colisão com ele. Sobre os resultados além da programação, conseguimos criar um repositório no Github contendo o desenvolvimento gradual do projeto, incluindo detalhes em forma de comentários e divisões entre funcionalidades através de branches.

### 6. Conclusão

Ao final do trabalho, podemos concluir o quão essencial é o uso de ferramentas de controle de versão, não só para organizar diferentes fluxos de trabalho em um mesmo projeto com vários colaboradores, como  também para documentar código até em projetos pessoais.

Com este trabalho aprendemos a trabalhar em equipe e a receber feedbacks dos nossos códigos, assim como utilizar as ferramentas de versionamento para auxiliar no desenvolvimento. Sem dúvida esses aprendizados serão muito importantes para projetos futuros, sejam pessoais ou profissionais, ao organizar e gerenciar projetos.

### 7. Anexos

**Capturas de tela do robô em ação:**

https://github.com/user-attachments/assets/2957f616-5b2b-43b2-b8f6-333edeae7e7f

https://github.com/user-attachments/assets/6e627b17-c16d-4afd-8e40-faca2e755752

<br>

**Ideia inicial do robô em gráfico:**

- Esta imagem foi criada no início do projeto
- Q1, Q2, Q3, Q4 representam os quadrantes de um campo
- Linhas contínuas representam o movimento do robô em um quadrante
- Linhas segmentadas representam o movimento do robô em um quadrante em uma outra camada. Infelizmente, não tivemos o tempo para implementar essa funcionalidade de camadas

<img height="400" alt="ideiaInicial" src="https://github.com/user-attachments/assets/fbec5032-3f5f-4c92-97b4-41ac6baeab30"/>

<br>
<br>

**Imagens da posição do robô em comparação aos outros:**

- Estas batalhas foram feitas enquanto o robô ainda estava em desenvolvimento

<img width="1600" height="583" alt="QuadWallPlacaresUm" src="https://github.com/user-attachments/assets/45d92b52-4232-41a4-ae3d-5a54ec1faee7" />
<img width="944" height="260" alt="QuadWallPlacaresDois" src="https://github.com/user-attachments/assets/b96dd799-f8be-4fbe-8fee-313b9e9d1501" />
<img width="944" height="260" alt="QuadWallPlacaresTres" src="https://github.com/user-attachments/assets/13ab34b3-886f-438b-b1f9-1a12d5412ced" />
