import { useState } from 'react';

function calculateWinner(squares) {
    const winningLines = [
        [0, 1, 2], [3, 4, 5], [6, 7, 8], [0, 3, 6], [1, 4, 7], [2, 5, 8], [0, 4, 8], [2, 4, 6]
    ];
    for (let i = 0; i < winningLines.length; i++) {
        const [a, b, c] = winningLines[i];
        if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c])
            return squares[a];
    }
    return null;
}

function Square({ symbol, classList, onSquareClick }) {
    return <button className={classList} onClick={onSquareClick}>{symbol}</button>;
}

function Restart({ ended, onClick }) {
    if (ended)
        return <p style={{cursor: 'pointer'}} onClick={onClick}>Restart</p>;
    return <p></p>;
}

function Board() {
    const [xIsNext, setXIsNext] = useState(true);
    const [squares, setSquares] = useState(Array(9).fill(null));
    const winner = calculateWinner(squares);
    let status = (winner) ? "Winner: " + winner :
        ((squares.includes(null)) ?  "Next player: " + (xIsNext ? 'X' : 'O') : "Game ended");
    function handleClick(i) {
        if (squares[i] || winner)
            return;
        const nextSquares = squares.slice();
        nextSquares[i] = (xIsNext) ? "X" : "O";
        setXIsNext(!xIsNext);
        setSquares(nextSquares);
    }
    function restartGame() {
        setXIsNext(true);
        setSquares(Array(9).fill(null));
    }
    return (
        <>
            <h1>{status}</h1>
            <div className="row">
                <Square symbol={squares[0]} onSquareClick={() => handleClick(0)} />
                <Square classList="middle_column" symbol={squares[1]} onSquareClick={() => handleClick(1)} />
                <Square symbol={squares[2]} onSquareClick={() => handleClick(2)} />
            </div>
            <div className="row">
                <Square classList="middle_row" symbol={squares[3]} onSquareClick={() => handleClick(3)} />
                <Square classList="middle_row middle_column" symbol={squares[4]} onSquareClick={() => handleClick(4)} />
                <Square classList="middle_row" symbol={squares[5]} onSquareClick={() => handleClick(5)} />
            </div>
            <div className="row">
                <Square symbol={squares[6]} onSquareClick={() => handleClick(6)} />
                <Square classList="middle_column" symbol={squares[7]} onSquareClick={() => handleClick(7)} />
                <Square symbol={squares[8]} onSquareClick={() => handleClick(8)} />
            </div>
            <Restart ended={winner || !squares.includes(null)} onClick={restartGame} />
        </>
    );
}

export default Board;
